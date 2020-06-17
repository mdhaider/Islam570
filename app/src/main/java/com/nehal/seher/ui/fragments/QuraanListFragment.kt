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
import androidx.recyclerview.widget.LinearLayoutManager
import com.nehal.seher.databinding.QuraanListFragmentBinding
import com.nehal.seher.factory.QuraanViewModelFactory
import com.nehal.seher.model.Para
import com.nehal.seher.model.Surah
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.retrofit.RetrofitBuilder
import com.nehal.seher.ui.adapters.QuraanParaAdapter
import com.nehal.seher.ui.adapters.QuraanSurahAdapter
import com.nehal.seher.utils.Status
import com.nehal.seher.utils.TopSpacingItemDecoration
import com.nehal.seher.viewmodels.QuraanViewModel

class QuraanListFragment : Fragment() {

    private lateinit var viewModel: QuraanViewModel
    private lateinit var paraAdapter: QuraanParaAdapter
    private lateinit var binding: QuraanListFragmentBinding
    private lateinit var navController: NavController
    private lateinit var paraList: List<Para>
    private lateinit var surahAdapter: QuraanSurahAdapter
    private var id: Int? = 0
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.let { QuraanListFragmentArgs.fromBundle(it).id }
        title = arguments?.let { QuraanListFragmentArgs.fromBundle(it).title }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuraanListFragmentBinding.inflate(inflater, container, false)
        setupViewModel()
        setupUI()

        if (id == 1) {
            setupObserversForSurah()
        } else setupObservers()

        return binding.root
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            QuraanViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(QuraanViewModel::class.java)
    }


    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(20)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)

        when (id) {
            1 -> initSurahAdapter()
            2 -> initParAdapter()
        }
    }

    private fun initSurahAdapter() {
        surahAdapter = QuraanSurahAdapter(arrayListOf())
        binding.recyclerView.adapter = surahAdapter
        surahAdapter.setItemAction { action, string ->
            run {
                when (string) {
                    "read" -> goToReadFragment(action)
                    "listen" -> goToListenFragment(action)
                }
            }

        }
    }

    private fun initParAdapter() {
        paraAdapter = QuraanParaAdapter(arrayListOf())
        binding.recyclerView.adapter = paraAdapter
        paraAdapter.setItemAction { action, string ->
            run {
                when (string) {
                    "read" -> goToReadFragment(action)
                    "listen" -> goToListenFragment(action)
                }
            }

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun goToReadFragment(para: Para) {
        val action =
            QuraanListFragmentDirections.actionParaRead(
                para.id,
                para.nameEn,
                para.textUrl
            ) //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }


    private fun goToListenFragment(para: Para) {
        val action =
            QuraanListFragmentDirections.actionParaListen(
                para.id,
                para.nameEn,
                para.audioUrl
            ) //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }

    private fun goToReadFragment(surah: Surah) {
        val action =
            QuraanListFragmentDirections.actionParaRead(
                surah.id,
                surah.nameEn,
                surah.textUrl
            ) //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }


    private fun goToListenFragment(surah: Surah) {
        val action =
            QuraanListFragmentDirections.actionParaListen(
                surah.id,
                surah.nameEn,
                surah.audioUrl
            ) //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }

    private fun setupObservers() {
        viewModel.getParas().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { surah -> retrieveList(surah) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(paraList: List<Para>) {
        this.paraList = paraList
        paraAdapter.apply {
            addParas(paraList)
            notifyDataSetChanged()
        }
    }

    private fun setupObserversForSurah() {
        viewModel.getAllSurah().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { surah -> retrieveListForSurah(surah) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveListForSurah(users: List<Surah>) {
        surahAdapter.apply {
            addSurahs(users)
            notifyDataSetChanged()
        }
    }
}
