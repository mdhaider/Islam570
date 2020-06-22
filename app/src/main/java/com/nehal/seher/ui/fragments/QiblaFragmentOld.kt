package com.nehal.seher.ui.fragments

import android.content.Context
import android.hardware.*
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import com.nehal.seher.databinding.FragmentQiblaBinding
import com.nehal.seher.model.LocationObject
import com.nehal.seher.utils.Constants
import com.nehal.seher.utils.ModelPreferences
import kotlinx.android.synthetic.main.fragment_qibla.*
import kotlin.math.roundToInt

class QiblaFragmentOld : Fragment() {
    private var currentDegree: Float = 0f
    private var currentNeedleDegree: Float = 0f
    private lateinit var needleAnimation: RotateAnimation
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var binding: FragmentQiblaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQiblaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initQiblaDirection(latitude!!, longitude!!)

    }

    private fun init() {
        needleAnimation = RotateAnimation(
            currentNeedleDegree,
            0f,
            Animation.RELATIVE_TO_SELF,
            .5f,
            Animation.RELATIVE_TO_SELF,
            .5f
        )
        val loc = ModelPreferences(requireContext()).getObject(
            Constants.LOCATION_OBJECT,
            LocationObject::class.java
        )
        latitude = loc?.latitude
        longitude = loc?.longitude

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    private fun initQiblaDirection(latitude: Double, longitude: Double) {
        val userLocation = Location("User Location")
        userLocation.latitude = latitude
        userLocation.longitude = longitude

        val destLocation = Location("Qibla Location")
        destLocation.latitude =
            QIBLA_LATITUDE
        destLocation.longitude =
            QIBLA_LONGITUDE

        sensorManager.registerListener(object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }

            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                val degree: Float = sensorEvent?.values?.get(0)?.roundToInt()?.toFloat()!!
                var head: Float = sensorEvent.values?.get(0)?.roundToInt()?.toFloat()!!


                var bearTo = userLocation.bearingTo(destLocation)
                val geoField = GeomagneticField(
                    userLocation.latitude.toFloat(),
                    userLocation.longitude.toFloat(),
                    userLocation.altitude.toFloat(),
                    System.currentTimeMillis()
                )

                head -= geoField.declination

                if (bearTo < 0) {
                    bearTo += 360
                }

                var direction = bearTo - head

                if (direction < 0) {
                    direction += 360
                }

               // tvHeading.text = "Heading : $degree + degrees"

                Log.d(TAG, "Needle Degree : $currentNeedleDegree, Direction : $direction")

                needleAnimation = RotateAnimation(
                    currentNeedleDegree,
                    direction,
                    Animation.RELATIVE_TO_SELF,
                    .5f,
                    Animation.RELATIVE_TO_SELF,
                    .5f
                )
                needleAnimation.fillAfter = true
                needleAnimation.duration = 200
                main_image_hands.startAnimation(needleAnimation)

                currentNeedleDegree = direction
                currentDegree = -degree

            }
        }, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    companion object {
        const val TAG = "MainActivity"
        const val QIBLA_LATITUDE = 21.3891
        const val QIBLA_LONGITUDE = 39.8579
    }
}