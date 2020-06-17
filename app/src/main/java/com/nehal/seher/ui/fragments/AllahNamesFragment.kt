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
import androidx.recyclerview.widget.LinearLayoutManager
import com.nehal.seher.databinding.AllahNamesListFragmentBinding
import com.nehal.seher.factory.HomeViewModelFactory
import com.nehal.seher.model.Names
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.retrofit.RetrofitBuilder
import com.nehal.seher.ui.adapters.NamesAdapter
import com.nehal.seher.utils.Status
import com.nehal.seher.utils.TopSpacingItemDecoration
import com.nehal.seher.viewmodels.HomeViewModel

class AllahNamesFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var namesAdapter: NamesAdapter
    private lateinit var binding: AllahNamesListFragmentBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllahNamesListFragmentBinding.inflate(inflater, container, false)
        setupViewModel()
        setupUI()
        setupObservers()
        return binding.root
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(HomeViewModel::class.java)
    }


    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        namesAdapter = NamesAdapter(arrayListOf())
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)
        binding.recyclerView.adapter = namesAdapter
        namesAdapter.setItemAction {
             updateDetails(it)
        }
    }

    private fun updateDetails(names:Names){
        binding.namesArabic.text= names.name
        binding.names.text= names.transliteration
        binding.meaning.text= names.meaning
        binding.benfits.text= names.benefits
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }


    private fun setupObservers() {
        viewModel.getAllNames("allah_names.json").observe(requireActivity(), Observer {
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

    private fun retrieveList(names: List<Names>) {
        namesAdapter.apply {
            addNames(names)
            notifyDataSetChanged()
        }
    }
}
