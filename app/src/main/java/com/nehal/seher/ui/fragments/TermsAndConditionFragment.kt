package com.nehal.seher.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nehal.seher.MainActivity
import com.nehal.seher.databinding.TermsAndConditionFragmentBinding
import com.nehal.seher.utils.AppPreferences
import com.nehal.seher.viewmodels.TermsAndConditionViewModel

class TermsAndConditionFragment : Fragment() {
    private lateinit var binding: TermsAndConditionFragmentBinding
    private lateinit var navController: NavController

    companion object {
        fun newInstance(message: String) = TermsAndConditionFragment()
    }

    private lateinit var viewModel: TermsAndConditionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).showOrHideToolbar(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TermsAndConditionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun goToOnboard2Screen() {
        val action =
            TermsAndConditionFragmentDirections.actionOnboard1To2()
        navController.navigate(action)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TermsAndConditionViewModel::class.java)

        binding.tvPrivacy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://mdhaider.github.io/privacy-policy/")
                )
            )
        }

        binding.btnContinue.setOnClickListener {
            AppPreferences.isFirstTimeInstall = false
            goToOnboard2Screen()

        }
    }

}