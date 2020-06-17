package com.nehal.seher.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import com.nehal.seher.MainActivity
import com.nehal.seher.databinding.AskPermissionFragmentBinding
import com.nehal.seher.viewmodels.AskPermissionViewModel

class AskPermissionFragment : Fragment() {
    private lateinit var binding: AskPermissionFragmentBinding
    private lateinit var navController: NavController

    private val quickPermissionsOption = QuickPermissionsOptions(
        handleRationale = false,
        handlePermanentlyDenied = false,
        permissionsDeniedMethod = { whenPermAreDenied(it) }
    )

    companion object {
        fun newInstance() = TermsAndConditionFragment()
    }

    private lateinit var viewModel: AskPermissionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).showOrHideToolbar(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AskPermissionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AskPermissionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.btnAllow.setOnClickListener {
            methodRequiresPermissions(quickPermissionsOption)
        }
    }

    private fun goToHomeScreen() {
        val action =
            AskPermissionFragmentDirections.actionOnboard2ToHome()
        navController.navigate(action)
    }

    private fun methodRequiresPermissions(quickPermissionsOptions: QuickPermissionsOptions) =
        runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            options = quickPermissionsOptions
        ) {
            goToHomeScreen()
        }

    private fun whenPermAreDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(requireContext())
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
            .setPositiveButton("Okay") { _, _ ->
                goToHomeScreen()
            }
            .setCancelable(false)
            .show()
    }
}