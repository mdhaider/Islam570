package com.nehal.seher.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import com.nehal.seher.MainActivity
import com.nehal.seher.databinding.AskPermissionFragmentBinding
import com.nehal.seher.model.LocationObject
import com.nehal.seher.utils.Constants
import com.nehal.seher.utils.ModelPreferences
import com.nehal.seher.utils.toast
import com.nehal.seher.viewmodels.AskPermissionViewModel

class AskPermissionFragment : Fragment() {
    private lateinit var binding: AskPermissionFragmentBinding
    private lateinit var navController: NavController

    private val quickPermissionsOption = QuickPermissionsOptions(
        handleRationale = false,
        handlePermanentlyDenied = false,
        permissionsDeniedMethod = { whenPermAreDenied(it) }
    )

    companion object {
        fun newInstance() = TermsAndConditionFragment()
    }

    private lateinit var viewModel: AskPermissionViewModel
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        (activity as MainActivity).showOrHideToolbar(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AskPermissionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AskPermissionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnAllow.setOnClickListener {
            methodRequiresPermissions(quickPermissionsOption)
        }
    }

    private fun goToHomeScreen() {
        val action =
            AskPermissionFragmentDirections.actionOnboard2ToHome()
        navController.navigate(action)
    }

    private fun methodRequiresPermissions(quickPermissionsOptions: QuickPermissionsOptions) =
        runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION, options = quickPermissionsOptions
        ) {
            getLastLocation()
        }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (isLocationEnabled()) {
            mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                var location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    ModelPreferences(requireContext()).putObject(
                        Constants.LOCATION_OBJECT,
                        LocationObject(location.latitude, location.longitude)
                    )
                    goToHomeScreen()
                }
            }
        } else {
            activity?.toast("Turn on location")
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_NO_POWER
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            ModelPreferences(requireContext()).putObject(
                Constants.LOCATION_OBJECT,
                LocationObject(mLastLocation.latitude, mLastLocation.longitude)
            )
            goToHomeScreen()
        }
    }


    private fun whenPermAreDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(requireContext())
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
            .setPositiveButton("Okay") { _, _ ->
                goToHomeScreen()
            }
            .setCancelable(false)
            .show()
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}