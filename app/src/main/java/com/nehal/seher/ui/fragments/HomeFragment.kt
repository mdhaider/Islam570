package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nehal.seher.MainActivity
import com.nehal.seher.R
import com.nehal.seher.databinding.HomeFragmentBinding
import com.nehal.seher.factory.HomeViewModelFactory
import com.nehal.seher.model.Dua
import com.nehal.seher.model.LocationObject
import com.nehal.seher.model.Poster
import com.nehal.seher.model.gregtohijri.HijriDateResponseData
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.retrofit.Retrof
import com.nehal.seher.ui.binders.*
import com.nehal.seher.utils.*
import com.nehal.seher.viewmodels.HomeViewModel
import mva2.adapter.ItemSection
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var navController: NavController
    private var dateFormat: SimpleDateFormat? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var duaList: ArrayList<Dua>
    private lateinit var posterList: ArrayList<Poster>
    private lateinit var adpater: MultiViewAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var hijriDateResponseData: HijriDateResponseData
    private val singleItems =
        arrayOf("One Day Ago", "Two Days Ago", "None", "One Day Ahead", "Two Days Ahead")
    private val checkedItem = 1
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        getLocation()
        setupViewModel()
        setGregCal()
        setupObserversForPrayerTimes("", latitude!!, longitude!!, "1")
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showOrHideToolbar(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(ApiHelper(Retrof.invoke(Const.ALADHAN_BASE_URL)))
        ).get(HomeViewModel::class.java)
    }

    private fun setUpAdapter(){
        // Create Adapter
        adpater = MultiViewAdapter()
        binding.rvHome.adapter = adpater

        layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = adpater.spanSizeLookup

        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.layoutManager = layoutManager

        val posterBinder= PosterBinder()
        val duaBinder= DuaBinder()
        val dateNowBinder= DateNowBinder()

        posterBinder.setItemAction {action, string ->
            when (string) {
                "more" -> activity?.toast(action.toString()+"more")
                "poster" -> activity?.toast(action.toString()+"poster")
            }
        }


        duaBinder.setItemAction {action, string ->
            when (string) {
                "more" -> activity?.toast(action.toString()+"more")
                "container" -> activity?.toast(action.toString()+"container")
            }
        }

        dateNowBinder.setItemAction {showPopupForDateAjdjustment()}

        // Register Binder
        adpater.registerItemBinders(HeaderBinder(), posterBinder, duaBinder, dateNowBinder)
        adpater.setSpanCount(layoutManager.spanCount)
        adpater.itemTouchHelper.attachToRecyclerView(binding.rvHome)

        // Create Section and add items
        duaList = ArrayList()
        duaList.add(Dua(5, "When Sneezing","يَرْحَمُكَ اللَّهُ" ,"Alhamdulillah", "Thanks and all praise be to Allah", "[Bukhari ,Mishkaat Shareef Pg.405]"))

        posterList = ArrayList()
        posterList.add(Poster(imgUrl = "https://i.picsum.photos/id/478/200/300.jpg?hmac=9XTsWr649TEW4EJf8V09OflQrYWLvD63zeYkUNJ8Aq4"))

        // Create Section and add items
        val listSection: ListSection<Poster> = ListSection<Poster>()
        listSection.addAll(posterList)

        val itemSection1: ItemSection<Header> =
            ItemSection<Header>(Header("Swipe and Drag Section"))

        val itemSection2: ItemSection<HijriDateResponseData> =
            ItemSection<HijriDateResponseData>(hijriDateResponseData)

        val listSection1: ListSection<Dua> = ListSection<Dua>()
        listSection1.addAll(duaList)

        adpater.addSection(itemSection2)
        adpater.addSection(listSection)
        adpater.addSection(itemSection1)
        adpater.addSection(listSection1)
    }

    private fun getLocation() {
        val loc = ModelPreferences(requireContext()).getObject(
            Constants.LOCATION_OBJECT,
            LocationObject::class.java
        )
        latitude = loc?.latitude
        longitude = loc?.longitude
    }


    private fun goToNextFragment(id: Int) {
        when (id) {
            1 -> goToNames()
            2 -> goToDua()
            3 -> goToUrduPosters()
        }
    }

    private fun goToNames() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToNames() //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }

    private fun goToDua(){
        val action = HomeFragmentDirections.actionHomeFragmentToDua() //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }
    private fun goToUrduPosters(){
        val action = HomeFragmentDirections.actionHomeFragmentToUrduPosters() //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }

    private fun setGregCal(){
       val calendar = Calendar.getInstance()
        dateFormat =  SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = dateFormat!!.format(calendar.time)
        val adjustment=0
       setupObservers(date,adjustment )
    }

    private fun setupObservers(date:String, adjustment:Int?) {
        viewModel.getHijriDateFromGregDate(date, adjustment).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { hijirDate ->
                            hijriDateResponseData= hijirDate.hijriDateResponseData
                            setUpAdapter()
                        }
                    }
                    Status.ERROR -> {
                        Timber.d(it.message)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
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
                          //  setData(prayerTimes.prayerTimesData.timings)
                           // binding.progressBar.visibility = View.GONE

                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                       // binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        //binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


    private fun showPopupForDateAjdjustment(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dlg_hj_date_adj_title))
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                Timber.d("btn poress")
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                Timber.d(which.toString())
            }
            .show()
    }
}

