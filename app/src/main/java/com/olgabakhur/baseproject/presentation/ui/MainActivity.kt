package com.olgabakhur.baseproject.presentation.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.ActivityMainBinding
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenCreated
import com.olgabakhur.baseproject.presentation.extensions.getCurrentDestinationId
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.NetworkConnectivityHelper
import com.olgabakhur.baseproject.presentation.util.device.DeviceManager
import com.olgabakhur.baseproject.presentation.util.device.DeviceType
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
import com.olgabakhur.baseproject.presentation.util.viewmodel.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.rootMainActivity)
    private val viewModel: MainViewModel by viewModel { App.appComponent.mainViewModel }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupOrientationChange()
        initNavController()
        setupToolbarNavigation()
        setupSystemBackButton()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun observeViewModel() {
        collectLatestWhenCreated(viewModel.getFlowApplicationErrors()) { appError ->
            Dialog.showOkDialogWithTitle(
                this,
                R.string.general_error_label,
                appError.message(this)
            )
        }

        collectLatestWhenCreated(viewModel.flowNetworkConnectivityStatus) { connectivity ->
            showSnackbar(
                this,
                binding.root,
                NetworkConnectivityHelper.getLocalizedName(connectivity, this)
            )
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setupOrientationChange() {
        when (DeviceManager.getDeviceType()) {
            DeviceType.Phone -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            DeviceType.TV -> requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> ActivityInfo.SCREEN_ORIENTATION_USER /* allow screen rotation for Tablets */
        }
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupToolbarNavigation() {
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.articlesFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupSystemBackButton() {
        onBackPressedDispatcher.addCallback(this) {
            when (navController.getCurrentDestinationId()) {
                R.id.articlesFragment -> finish()
                else -> navController.navigateUp()
            }
        }
    }
}