package com.nehal.seher

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import com.nehal.seher.databinding.ActivityMainBinding
import com.nehal.seher.utils.toast
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var navGraph: NavGraph

    private val quickPermissionsOption = QuickPermissionsOptions(
        rationaleMessage = "Custom rational message",
        permanentlyDeniedMessage = "Custom permanently denied message",
        rationaleMethod = { rationaleCallback(it) },
        permanentDeniedMethod = { permissionsPermanentlyDenied(it) },
        permissionsDeniedMethod = { whenPermAreDenied(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get token
        if (checkGooglePlayServices()) {
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, getString(R.string.token_error), task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg = getString(R.string.token_prefix, token)
                    Log.d(TAG, msg)
                    // Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                })
            // [END retrieve_current_token]
        } else {
            //You won't be able to send notifications to this device
            Log.w(TAG, "Device doesn't have google play services")
        }

        //    methodRequiresPermissions(quickPermissionsOption)

        navController = findNavController(R.id.main_nav_host)
        val graphInflater = navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.nav_graph)

        val destination = if (intent.getBooleanExtra(
                IS_PRIVACY_POLICY_ACCEPTED,
                false
            )
        ) R.id.onboarding1Fragment else R.id.homeFragment
        navGraph.startDestination = destination
        navController.graph = navGraph

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.accountsFragment,
            R.id.quraanFragment,
            R.id.moreFragment,
            R.id.prayerTimesFragment,
            R.id.posterFragment
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setDrawerLayout(binding.mainDrawerLayout) //Pass the drawer layout id from activity xml
            .build()

        setSupportActionBar(binding.mainToolbar) //Set toolbar

        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        ) //Setup toolbar with back button and drawer icon according to appBarConfiguration

        visibilityNavElements(navController) //If you want to hide drawer or bottom navigation configure that in this function

        val drawerNavController = Navigation.findNavController(this, R.id.main_nav_host)
        NavigationUI.setupWithNavController(binding.mainNavigationView, drawerNavController)
        drawerNavController.addOnDestinationChangedListener { _, destination1, _ ->
            when (destination1.id) {
                R.id.aboutUsFrgament -> toast("hehe")

                else -> ""
            }
        }
    }

    private fun visibilityNavElements(navController: NavController) {

        //Listen for the change in fragment (navigation) and hide or show drawer or bottom navigation accordingly if required
        //Modify this according to your need
        //If you want you can implement logic to deselect(styling) the bottom navigation menu item when drawer item selected using listener

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment -> hideBottomNavigation()
                R.id.qiblaFragment -> hideBottomNavigation()
                R.id.duaFragment -> hideBottomNavigation()
                R.id.duaCategoryFragment -> hideBottomNavigation()
                R.id.quraanParaListenFragment -> hideBottomNavigation()
                R.id.quraanParaReadFragment -> hideBottomNavigation()
                R.id.quraanListFragment -> hideBottomNavigation()
                R.id.namesFragment -> hideBottomNavigation()
                R.id.hadithFragment -> hideBottomNavigation()
                R.id.posterDetailFragment -> hideBottomNavigation()
                R.id.prayerTimesSettingsFragment -> hideBottomNavigation()
                R.id.manualCorrectionFragment -> hideBottomNavigation()
                R.id.onboarding1Fragment -> hideBothNavigation()
                R.id.onboarding2Fragment -> hideBothNavigation()
                R.id.aboutUsFrgament -> toast("hehe")

                else -> showBothNavigation()
            }
        }
    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.GONE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    fun showOrHideToolbar(shouldShow: Boolean) {
        if (shouldShow) {
            binding.mainToolbar.visibility = View.VISIBLE
        } else {
            binding.mainToolbar.visibility = View.GONE
        }
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer

        binding.mainNavigationView.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        binding.mainBottomNavigationView.visibility = View.VISIBLE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        binding.mainNavigationView.setupWithNavController(navController) //Setup Drawer navigation with navController
        binding.mainBottomNavigationView.setupWithNavController(navController) //Setup Bottom navigation with navController
    }

    fun exitApp() { //To exit the application call this function from fragment
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }

    private fun methodRequiresPermissions(quickPermissionsOptions: QuickPermissionsOptions) =
        runWithPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            options = quickPermissionsOptions
        ) {
            /*  val toast = Toast.makeText(this, "Cal and microphone permission granted", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()*/
        }

    private fun rationaleCallback(req: QuickPermissionsRequest) {
        AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom rationale dialog. Please allow us to proceed " + "asking for permissions again, or cancel to end the permission flow.")
            .setPositiveButton("Go Ahead") { dialog, which -> req.proceed() }
            .setNegativeButton("cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun permissionsPermanentlyDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage(
                "This is the custom permissions permanently denied dialog. " +
                        "Please open app settings to open app settings for allowing permissions, " +
                        "or cancel to end the permission flow."
            )
            .setPositiveButton("App Settings") { dialog, which -> req.openAppSettings() }
            .setNegativeButton("Cancel") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun whenPermAreDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
            .setPositiveButton("OKAY") { _, _ -> }
            .setCancelable(false)
            .show()
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // text_view_notification.text = intent.extras?.getString("message")
        }
    }

    private fun checkGooglePlayServices(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return if (status != ConnectionResult.SUCCESS) {
            Timber.e("Error")
            // ask user to update google play services.
            false
        } else {
            Timber.i("Google play services updated")
            true
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val IS_PRIVACY_POLICY_ACCEPTED = "isPrivacyPolicyAccepted"
        fun open(context: Context, isAcceptedNewPrivacyPolicies: Boolean) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra(IS_PRIVACY_POLICY_ACCEPTED, isAcceptedNewPrivacyPolicies)
            })
        }
    }
}
