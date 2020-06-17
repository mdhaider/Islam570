package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.commit451.modalbottomsheetdialogfragment.ModalBottomSheetDialogFragment
import com.commit451.modalbottomsheetdialogfragment.Option
import com.nehal.seher.R
import com.nehal.seher.databinding.PosterListFragmentBinding
import com.nehal.seher.factory.HomeViewModelFactory
import com.nehal.seher.model.Poster
import com.nehal.seher.retrofit.ApiHelper
import com.nehal.seher.retrofit.RetrofitBuilder
import com.nehal.seher.ui.adapters.PosterListAdapter
import com.nehal.seher.utils.Status
import com.nehal.seher.utils.TopSpacingItemDecoration
import com.nehal.seher.viewmodels.HomeViewModel


class PosterListFragment : Fragment(),ModalBottomSheetDialogFragment.Listener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var posterListAdapter: PosterListAdapter
    private lateinit var binding: PosterListFragmentBinding
    private lateinit var navController: NavController
    private var id: Int? = 0
    private var title: String? = null
    private lateinit var listPoster: List<Poster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.let { QuraanListFragmentArgs.fromBundle(it).id }
        title = arguments?.let { QuraanListFragmentArgs.fromBundle(it).title }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PosterListFragmentBinding.inflate(inflater, container, false)
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
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)
        val topSpacingDecorator = TopSpacingItemDecoration(20)
        binding.recyclerView.addItemDecoration(topSpacingDecorator)
        initPosterAdapter()
    }

    private fun initPosterAdapter() {
        posterListAdapter = PosterListAdapter(arrayListOf())
        binding.recyclerView.adapter = posterListAdapter
        posterListAdapter.setItemAction { action ->
            run {
                goToPosterDetailFragment(action)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_poster, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lang -> showBottomSheetDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToPosterDetailFragment(poster: Poster) {
        val action =
            PosterListFragmentDirections.actionPosterListToDetail(
                poster
            ) //if needed pass values to frag here NB: add that arguments to nav_graph also
        navController.navigate(action) //navigation
    }


    private fun setupObservers() {
        viewModel.getAllUrduPosters().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { urduPoster ->
                            retrieveListForUrduPoster(urduPoster)
                            listPoster = urduPoster
                        }
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

    private fun retrieveListForUrduPoster(poster: List<Poster>) {
        posterListAdapter.apply {
            addUrduPosters(poster)
            notifyDataSetChanged()
        }
    }

    private fun showBottomSheetDialog() {
        ModalBottomSheetDialogFragment.Builder()
            .add(R.menu.menu_lang)
            .layout(R.layout.item_custom)
            .columns(1)
            .show(childFragmentManager, "HI")
    }

    override fun onModalOptionSelected(tag: String?, option: Option) {
        Log.d("drd", "dd")
/*
        Snackbar.make(root, "Selected option ${option.title} from tag $tag", Snackbar.LENGTH_SHORT)
            .show()*/
        val lists: ArrayList<Poster> = ArrayList()
        val map = listPoster.groupBy { it.category }

        when (option.id) {
            0 -> {
                map.forEach() {
                    when (it.key) {
                        "english" ->
                            lists.addAll(it.value)
                    }
                }
            }
            1 -> {
                map.forEach() {
                    when (it.key) {
                        "urdu" ->
                            lists.addAll(it.value)
                    }
                }
                retrieveListForUrduPoster(lists)
            }
        }
    }
}