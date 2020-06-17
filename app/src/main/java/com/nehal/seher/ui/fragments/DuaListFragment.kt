package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nehal.seher.databinding.DuaListFragmentBinding
import com.nehal.seher.model.DuaModel
import com.nehal.seher.ui.adapters.DuaListAdapter
import com.nehal.seher.utils.TopSpacingItemDecoration

class DuaListFragment : Fragment() {
    private lateinit var duaListAdapter: DuaListAdapter
    private lateinit var binding: DuaListFragmentBinding
    private lateinit var navController: NavController
    private lateinit var duaList: Array<DuaModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DuaListFragmentBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        duaListAdapter = DuaListAdapter(arrayListOf())
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)
        binding.recyclerView.adapter = duaListAdapter
        duaListAdapter.setItemAction {
            goToNextFragment(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        duaList = arguments?.let { DuaListFragmentArgs.fromBundle(it).item }!!

        retrieveList()
    }

    private fun goToNextFragment(dua: DuaModel) {
        val action =
            DuaListFragmentDirections.actionDuaListToDua(
                dua,
                dua.dua_title!!
            )
        navController.navigate(action) //navigation
    }


    private fun retrieveList() {
        duaListAdapter.apply {
            addDuasForCat(duaList.toList())
            notifyDataSetChanged()
        }
    }
}
