package com.nehal.seher.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.nehal.seher.R
import com.nehal.seher.databinding.QiblaFragmentBinding
import com.nehal.seher.utils.AppPreferences
import com.nehal.seher.utils.Compass
import com.nehal.seher.utils.Compass.CompassListener
import com.nehal.seher.utils.GPSTracker
import com.nehal.seher.utils.LocationUtils
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class QiblaFragment : Fragment() {
    private var compass: Compass? = null
    private var qiblatIndicator: ImageView? = null
    private var imageDial: ImageView? = null
    private var tvAngle: TextView? = null
    private var tvYourLocation: TextView? = null
    private var currentAzimuth = 0f
    private var gps: GPSTracker? = null
    private val RC_Permission = 1221
    private lateinit var binding: QiblaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QiblaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gps = GPSTracker(requireActivity())
        qiblatIndicator = binding.qiblaIndicator
        imageDial = binding.dial
        tvAngle = binding.tvDegreesFromNorth
        tvYourLocation = binding.tvLoc
        qiblatIndicator!!.visibility = View.INVISIBLE
        qiblatIndicator!!.visibility = View.GONE
        setupCompass()
        showDistanceBetween()
        setLocation()
    }

    override fun onStart() {
        super.onStart()
        if (compass != null) {
            compass!!.start(requireActivity())
        }
    }

    override fun onPause() {
        super.onPause()
        if (compass != null) {
            compass!!.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (compass != null) {
            compass!!.start(requireActivity())
        }
    }

    override fun onStop() {
        super.onStop()
        if (compass != null) {
            compass!!.stop()
        }
        if (gps != null) {
            gps!!.stopUsingGPS()
            gps = null
        }
    }

    private fun setupCompass() {

        if (!AppPreferences.isPermissionGranted) {
            getBearing()
        } else {
            tvAngle!!.text = resources.getString(R.string.msg_permission_not_granted_yet)
          //  tvYourLocation!!.text = resources.getString(R.string.msg_permission_not_granted_yet)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    RC_Permission
                )
            } else {
                fetch_GPS()
            }
        }
        compass = Compass(requireActivity())
        val cl = CompassListener { azimuth: Float ->
            adjustGambarDial(azimuth)
            adjustArrowQiblat(azimuth)
        }
        compass!!.setListener(cl)
    }

    fun adjustGambarDial(azimuth: Float) {
        val an: Animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        imageDial!!.startAnimation(an)
    }

    fun adjustArrowQiblat(azimuth: Float) {
        val kiblat_derajat = AppPreferences.qiblaDerajat
        val an: Animation = RotateAnimation(
            -currentAzimuth + kiblat_derajat, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        qiblatIndicator!!.startAnimation(an)
        if (kiblat_derajat > 0) {
            qiblatIndicator!!.visibility = View.VISIBLE
        } else {
            qiblatIndicator!!.visibility = View.INVISIBLE
            qiblatIndicator!!.visibility = View.GONE
        }
    }

     fun getBearing() {
        // Get the location manager
         val kaabaDegs = AppPreferences.qiblaDerajat
        if (kaabaDegs > 0.0001) {
            val strYourLocation: String
            strYourLocation =
                if (gps!!.getLocation() != null) resources.getString(R.string.your_location) + " " + gps!!.getLocation().latitude + ", " + gps!!.getLocation()
                    .longitude else resources.getString(R.string.unable_to_get_your_location)
           // tvYourLocation!!.text = strYourLocation
            val strKaabaDirection = String.format(
                Locale.ENGLISH,
                "%.0f",
                kaabaDegs
            ) + " " + resources.getString(R.string.degree) + " " + getDirectionString(kaabaDegs)
            tvAngle!!.text = strKaabaDirection
            qiblatIndicator!!.visibility = View.VISIBLE
        } else {
            fetch_GPS()
        }
    }

    private fun getDirectionString(azimuthDegrees: Float): String {
        var where = "NW"
        if (azimuthDegrees >= 350 || azimuthDegrees <= 10) where = "N"
        if (azimuthDegrees < 350 && azimuthDegrees > 280) where = "NW"
        if (azimuthDegrees <= 280 && azimuthDegrees > 260) where = "W"
        if (azimuthDegrees <= 260 && azimuthDegrees > 190) where = "SW"
        if (azimuthDegrees <= 190 && azimuthDegrees > 170) where = "S"
        if (azimuthDegrees <= 170 && azimuthDegrees > 100) where = "SE"
        if (azimuthDegrees <= 100 && azimuthDegrees > 80) where = "E"
        if (azimuthDegrees <= 80 && azimuthDegrees > 10) where = "NE"
        return where
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == RC_Permission) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                // permission was granted, yay! Do the
                AppPreferences.isPermissionGranted = true
                tvAngle!!.text = resources.getString(R.string.msg_permission_granted)
               // tvYourLocation!!.text = resources.getString(R.string.msg_permission_granted)
                qiblatIndicator!!.visibility = View.INVISIBLE
                qiblatIndicator!!.visibility = View.GONE
                fetch_GPS()
            } else {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.toast_permission_required),
                    Toast.LENGTH_LONG
                ).show()
                //requireActivity().finish();
            }
        }
    }

    fun fetch_GPS() {
        val result: Double
        gps = GPSTracker(requireActivity())
        if (gps!!.canGetLocation()) {
            val myLat = gps!!.getLatitude()
            val myLng = gps!!.getLongitude()
            // \n is for new line
            val strYourLocation = (resources.getString(R.string.your_location)
                    + " " + myLat + ", " + myLng)
          //  tvYourLocation!!.text = strYourLocation
            if (myLat < 0.001 && myLng < 0.001) {
                qiblatIndicator!!.visibility = View.INVISIBLE
                qiblatIndicator!!.visibility = View.GONE
                tvAngle!!.text = resources.getString(R.string.location_not_ready)
               // tvYourLocation!!.text = resources.getString(R.string.location_not_ready)
            } else {
                val kaabaLng = 39.826206
                val kaabaLat = Math.toRadians(21.422487)
                val myLatRad = Math.toRadians(myLat)
                val longDiff = Math.toRadians(kaabaLng - myLng)
                val y = Math.sin(longDiff) * Math.cos(kaabaLat)
                val x =
                    cos(myLatRad) * sin(kaabaLat) - sin(
                        myLatRad
                    ) * cos(kaabaLat) * cos(longDiff)
                result = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
                AppPreferences.qiblaDerajat = result.toFloat()
                val strKaabaDirection = String.format(
                    Locale.ENGLISH,
                    "%.0f",
                    result.toFloat()
                ) + " " + resources.getString(R.string.degree) + " " + getDirectionString(
                    result.toFloat()
                )
                tvAngle!!.text = strKaabaDirection
                qiblatIndicator!!.visibility = View.VISIBLE
            }
        } else {
            gps!!.showSettingsAlert()
            qiblatIndicator!!.visibility = View.INVISIBLE
            qiblatIndicator!!.visibility = View.GONE
            tvAngle!!.text = resources.getString(R.string.pls_enable_location)
        }
    }

    private fun showDistanceBetween(){
       val distnce= LocationUtils.calculateDistance(gps!!.latitude, gps!!.longitude, 21.422487, 39.826206)
        val distnaceinIm= LocationUtils.distanceText(distnce)

        binding.tvDistance.text= distnaceinIm
    }

    private fun setLocation(){
        val loc= LocationUtils.getCityNameFromLatLong(gps!!.latitude, gps!!.longitude)
        binding.tvLoc.text= loc
    }
}