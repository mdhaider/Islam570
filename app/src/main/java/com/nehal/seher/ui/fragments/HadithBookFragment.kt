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
import com.nehal.seher.databinding.HadisFragmentBinding
import com.nehal.seher.factory.HadithCollectionModelFactory
import com.nehal.seher.model.hadith.HadithBook
import com.nehal.seher.model.hadith.HadithCollection
import com.nehal.seher.room.HadithDatabase
import com.nehal.seher.room.HadithDatabseHelper
import com.nehal.seher.ui.adapters.HadisAdapter
import com.nehal.seher.ui.adapters.HadisBookAdapter
import com.nehal.seher.utils.Status
import com.nehal.seher.utils.TopSpacingItemDecoration
import com.nehal.seher.viewmodels.HadithListViewModel
import timber.log.Timber

class HadithBookFragment : Fragment() {
    private lateinit var viewModel: HadithListViewModel
    private lateinit var hadisAdapter: HadisBookAdapter
    private lateinit var binding: HadisFragmentBinding
    private lateinit var navController: NavController
    private var id: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HadisFragmentBinding.inflate(inflater, container, false)
        setupViewModel()
        setupUI()
        setupObservers()

        return binding.root
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            HadithCollectionModelFactory(
                HadithDatabseHelper(
                    HadithDatabase.getInstance(requireContext())?.clientDao()!!
                )
            )
        ).get(HadithListViewModel::class.java)
    }


    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        hadisAdapter = HadisBookAdapter(arrayListOf())
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)
        binding.recyclerView.adapter = hadisAdapter
        hadisAdapter.setItemAction {
                goToNextFragment(it)
            }

        }

    private fun goToNextFragment(hadith: HadithBook) {
      //  val action =
        //    DuaListFragmentDirections.actionDuaListToDua(dua) //if needed pass values to frag here NB: add that arguments to nav_graph also
      //  navController.navigate(action) //navigation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun setupObservers() {
        viewModel.getAllBook().observe(requireActivity(), Observer {
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
                        Timber.d(it.message)
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

    private fun retrieveList(bookList: List<HadithBook>) {
        hadisAdapter.apply {
            addHadithBook(bookList)
            notifyDataSetChanged()
        }
    }
}
