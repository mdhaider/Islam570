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
import com.nehal.seher.databinding.OnboardingScreen1FragmentBinding
import com.nehal.seher.model.Poster


class OnboardingScreen1Fragment : Fragment() {
    private lateinit var binding:OnboardingScreen1FragmentBinding
    private lateinit var navController: NavController
    companion object {
        fun newInstance(message: String)= OnboardingScreen1Fragment()
        }

    private lateinit var viewModel: OnboardingScreen1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).showOrHideToolbar(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= OnboardingScreen1FragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() //Initialising navController
    }

    private fun goToOnboard2Screen() {
        val action =
            OnboardingScreen1FragmentDirections.actionOnboard1To2()
        navController.navigate(action)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OnboardingScreen1ViewModel::class.java)

        binding.tvPrivacy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://mdhaider.github.io/privacy-policy/")
                )
            )
        }

        binding.btnContinue.setOnClickListener {
            goToOnboard2Screen()

        }
    }

}