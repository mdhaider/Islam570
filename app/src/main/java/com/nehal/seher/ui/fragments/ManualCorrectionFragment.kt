package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nehal.seher.R
import com.nehal.seher.databinding.ManualCorrectionFragmentBinding
import com.nehal.seher.repository.ManualCorrectionRepository
import com.nehal.seher.ui.adapters.ManualCorrectionAdapter
import com.nehal.seher.viewmodels.ManualCorrectionViewModel


class ManualCorrectionFragment : Fragment() {
    private lateinit var binding: ManualCorrectionFragmentBinding
    private lateinit var navController: NavController
    private lateinit var manualCorrectionAdapter: ManualCorrectionAdapter
    private val corrArray = arrayOfNulls<String>(121)

    companion object {
        fun newInstance() = ManualCorrectionFragment()
    }

    private lateinit var viewModel: ManualCorrectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManualCorrectionFragmentBinding.inflate(inflater, container, false)
        initRecyclerView()
        addDataSet()
        createArray()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManualCorrectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun addDataSet() {
        val data = ManualCorrectionRepository.createDataSet()
        manualCorrectionAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            manualCorrectionAdapter = ManualCorrectionAdapter()
            adapter = manualCorrectionAdapter

            manualCorrectionAdapter.setItemAction {
               showManualCorrDialog(it.id)

            }

        }

    }

    private fun showManualCorrDialog(id: Int) {
        val checkedItem = 60

        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(corrArray, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    private fun createArray(){
        for(i in 0..120){
            corrArray[i] = (i-60).toString()+" "+"minutes"
        }
    }
}