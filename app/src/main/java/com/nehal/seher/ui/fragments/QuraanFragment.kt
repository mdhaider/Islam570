package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nehal.seher.databinding.QuraanFragmentBinding
import com.nehal.seher.repository.QuraanListRepository
import com.nehal.seher.ui.adapters.QuraanAdapter
import com.nehal.seher.utils.TopSpacingItemDecoration

class QuraanFragment : Fragment() {
    private lateinit var quraanAdapter: QuraanAdapter
    private lateinit var binding: QuraanFragmentBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuraanFragmentBinding.inflate(inflater, container, false)
        initRecyclerView()
        addDataSet()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun addDataSet() {
        val data = QuraanListRepository.createDataSet()
        quraanAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            val topSpacingDecorator =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            quraanAdapter = QuraanAdapter()
            adapter = quraanAdapter

            quraanAdapter.setItemAction {
              goToNextFragment(it.id)

            }
        }
    }

    private fun goToNextFragment(id: Int) {
        var title: String?=null
        when (id) {
            1 -> title = "Quran Surah"
            2 -> title = "Quran Para"
        }
        val action = QuraanFragmentDirections.actionQuraanSuraPara(
            id, title!!) //if needed pass values to frag here NB: add that arguments to nav_graph also
                navController.navigate(action) //navigation
    }
}
