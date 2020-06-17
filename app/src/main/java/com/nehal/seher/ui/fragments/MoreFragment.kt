package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nehal.seher.databinding.MoreFragmentBinding
import com.nehal.seher.repository.MoreRepository
import com.nehal.seher.ui.adapters.MoreAdapter
import com.nehal.seher.utils.TopSpacingItemDecoration

class MoreFragment : Fragment() {
    private lateinit var binding: MoreFragmentBinding
    private lateinit var moreAdapter: MoreAdapter
    private lateinit var navController: NavController

    companion object {
        fun newInstance() = MoreFragment()
    }

    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoreFragmentBinding.inflate(inflater, container, false)
        initRecyclerView()
        addDataSet()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            val topSpacingDecorator =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            moreAdapter = MoreAdapter()
            adapter = moreAdapter

            moreAdapter.setItemAction {
                goToNextFragment(it.id)

            }
        }
    }

    private fun goToNextFragment(id: Int) {
        when (id) {
            1 -> goToNames()
            2 -> goToDua()
            3 -> goToQibla()
            4 -> goToHadith()
        }
    }

    private fun goToNames() {
        val action = MoreFragmentDirections.actionMoreToNames()
        navController.navigate(action)
    }

    private fun goToDua() {
        val action = MoreFragmentDirections.actionMoreToDua()
        navController.navigate(action)
    }

    private fun goToQibla() {
        val action = MoreFragmentDirections.actionMoreToQibla()
        navController.navigate(action)
    }

    private fun goToHadith() {
        val action = MoreFragmentDirections.actionMoreToHadith()
        navController.navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoreViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun addDataSet() {
        val data = MoreRepository.createMoreItems()
        moreAdapter.submitList(data)
    }

}