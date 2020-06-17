package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nehal.seher.R
import com.nehal.seher.databinding.PrayerTimesSettingsFragmentBinding
import com.nehal.seher.hqibla.GPSTracker
import com.nehal.seher.repository.PrayerTimesSetingRepository
import com.nehal.seher.ui.adapters.PrayerSettingsAdapter
import com.nehal.seher.utils.LocationUtils
import timber.log.Timber


class PrayerTimesSettingsFragment : Fragment() {
    private lateinit var binding: PrayerTimesSettingsFragmentBinding
    private lateinit var navController: NavController
    private lateinit var prayerSettingsAdapter: PrayerSettingsAdapter
    private lateinit var gpsTracker: GPSTracker

    companion object {
        fun newInstance() = PrayerTimesSettingsFragment()
    }

    private lateinit var viewModel: PrayerTimesSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PrayerTimesSettingsFragmentBinding.inflate(inflater, container, false)
        initRecyclerView()
        addDataSet()
        getLocation()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PrayerTimesSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun addDataSet() {
        val data = PrayerTimesSetingRepository.createDataSet()
        prayerSettingsAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            prayerSettingsAdapter = PrayerSettingsAdapter()
            adapter = prayerSettingsAdapter

            prayerSettingsAdapter.setItemAction {
                goToNextFragment(it.id)

            }

        }

    }

    private fun goToNextFragment(id: Int) {
        when (id) {
            1 -> showLocationPopUp()
            2 -> showAdhanMethodPopUp()
            3 -> manualCorr()
            4 -> showAAsrTimePopUp()
            5 -> showHighAltPopUp()
            6 -> showDaySavingPopUp()

        }
    }

    private fun manualCorr() {
        val action = PrayerTimesSettingsFragmentDirections.actionPrayerSettingsToCorrection()
        navController.navigate(action)
    }

    private fun showLocationPopUp() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_loc))
            .setMessage(resources.getString(R.string.dlg_loc_msg))
            .setPositiveButton(resources.getString(R.string.dlg_loc_btn)) { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }

    private fun showAdhanMethodPopUp(){
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_prater_time_conv))
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(R.array.adhan_method, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    private fun showAAsrTimePopUp(){
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_asr_time))
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(R.array.asr_time, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }
    private fun showHighAltPopUp(){
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_asr_time))
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(R.array.high_alt, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    private fun showDaySavingPopUp() {
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_daylight_saving))
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(R.array.daylight_saving, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    private fun getLocation() {
        gpsTracker = GPSTracker(requireActivity())
        if (gpsTracker.canGetLocation()) {
            val myLat = gpsTracker.latitude
            val myLng = gpsTracker.longitude
            val cityName = LocationUtils.getCityNameFromLatLong(myLat, myLng)
            Timber.d(cityName)
        }
    }
}