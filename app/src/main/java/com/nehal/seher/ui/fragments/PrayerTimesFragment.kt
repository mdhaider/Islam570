package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nehal.seher.MainActivity
import com.nehal.seher.R
import com.nehal.seher.databinding.PrayerTimesFragmentBinding
import com.nehal.seher.factory.HomeViewModelFactory
import com.nehal.seher.model.LocationObject
import com.nehal.seher.model.prayertimes.Timings
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.retrofit.Retrof
import com.nehal.seher.utils.*
import com.nehal.seher.viewmodels.HomeViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PrayerTimesFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: PrayerTimesFragmentBinding
    private lateinit var navController: NavController
    private var latitude:Double?=null
    private var longitude:Double?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showOrHideToolbar(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PrayerTimesFragmentBinding.inflate(inflater, container, false)
        binding.progressBar.visibility=View.VISIBLE
        getLocation()
        setupViewModel()
        setupObserversForPrayerTimes("", latitude!!, longitude!!, "1")
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_prayers, menu)
    }

    private fun getLocation(){
        val loc= ModelPreferences(requireContext()).getObject(Constants.LOCATION_OBJECT, LocationObject::class.java)
        latitude=loc?.latitude
        longitude=loc?.longitude
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> goToSettings()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {
            val action =
                PrayerTimesFragmentDirections.actionPrayerTimesSettings() //if needed pass values to frag here NB: add that arguments to nav_graph also
            navController.navigate(action) //navigation
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(ApiHelper(Retrof.invoke(Const.ALADHAN_BASE_URL)))
        ).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        navController = findNavController() //Initialising navController
    }


    private fun setupObserversForPrayerTimes(
        dateOrTimestamp: String,
        latitude: Double,
        longitude: Double,
        method: String
    ) {
        viewModel.getPrayerTimes(
            dateOrTimestamp,
            latitude,
            longitude,
            method
        ).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { prayerTimes ->
                            setData(prayerTimes.prayerTimesData.timings)
                            binding.progressBar.visibility = View.GONE

                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setData(timings: Timings) {
        binding.tvSunrise.text = getTimeInAMPM(timings.sunrise)
        binding.tvSunset.text = getTimeInAMPM(timings.sunset)
        binding.tvFajr.text = getTimeInAMPM(timings.fajr)
        binding.tvDhuhr.text = getTimeInAMPM(timings.dhuhr)
        binding.tvAsr.text = getTimeInAMPM(timings.asr)
        binding.tvMaghrib.text = getTimeInAMPM(timings.maghrib)
        binding.tvIsha.text = getTimeInAMPM(timings.isha)
        binding.tvLoc.text = LocationUtils.getCityNameFromLatLong(latitude!!,longitude!!)
    }

    private fun getTimeInAMPM(time: String): String {
        try {
            val sdf = SimpleDateFormat(DATE_INPUT_FORMAT, Locale.getDefault())
            val dateObj = sdf.parse(time)
            val formattedTime = SimpleDateFormat( DATE_OUTPUT_FORMAT, Locale.getDefault()).format(dateObj)
            return formattedTime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    companion object {
        const val DATE_INPUT_FORMAT = "H:mm"
        const val DATE_OUTPUT_FORMAT = "hh:mm aa"
    }
}
