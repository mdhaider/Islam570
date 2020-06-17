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
import com.nehal.seher.databinding.DuaListFragmentBinding
import com.nehal.seher.factory.DuaModelFactory
import com.nehal.seher.model.DuaModel
import com.nehal.seher.room.databses.DuaAndAdhkarDatabase
import com.nehal.seher.room.dbhelper.DuaDatabseHelper
import com.nehal.seher.ui.adapters.DuaCategoryAdapter
import com.nehal.seher.utils.Status
import com.nehal.seher.utils.TopSpacingItemDecoration
import com.nehal.seher.viewmodels.DuaViewModel

class DuaCategoryFragment : Fragment() {
    private lateinit var duaviewModel: DuaViewModel
    private lateinit var duasCategoryAdapter: DuaCategoryAdapter
    private lateinit var binding: DuaListFragmentBinding
    private lateinit var navController: NavController
    private lateinit var duaMap: Map<String, List<DuaModel>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DuaListFragmentBinding.inflate(inflater, container, false)
        setupViewModel()
        setupUI()
        setupObserversForDua()
        return binding.root
    }


    private fun setupViewModel() {
        duaviewModel = ViewModelProviders.of(
            this,
            DuaModelFactory(
                DuaDatabseHelper(
                    DuaAndAdhkarDatabase.getInstance(requireContext())?.duaDao()!!
                )
            )
        ).get(DuaViewModel::class.java)
    }


    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        duasCategoryAdapter = DuaCategoryAdapter(arrayListOf())
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)
        binding.recyclerView.adapter = duasCategoryAdapter
        duasCategoryAdapter.setItemAction {
            goToNextFragment(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun goToNextFragment(dua: String) {
        val list = duaMap[dua]?.toTypedArray()
        val action =
            DuaCategoryFragmentDirections.actionDuaCatToList(list, dua)
        navController.navigate(action)
    }

    private fun setupObserversForDua() {
        duaviewModel.getAllDuas().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { dua -> retrieveList(dua) }
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

    private fun retrieveList(names: List<DuaModel>) {
        duaMap = names.groupBy {
            it.category!!
        }

        val list: ArrayList<String> = ArrayList()

        duaMap.forEach {
            it.key.let { it1 -> list.add(it1) }
        }

        duasCategoryAdapter.apply {
            addDuas(list)
            notifyDataSetChanged()
        }
    }
}
