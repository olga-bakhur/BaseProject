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
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.ActivityMainBinding
import com.olgabakhur.baseproject.presentation.extensions.collectWhenCreated
import com.olgabakhur.baseproject.presentation.extensions.getCurrentDestinationId
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.device.DeviceManager
import com.olgabakhur.baseproject.presentation.util.device.DeviceType
import com.olgabakhur.baseproject.presentation.util.view.Dialog.showOkDialogWithTitle
import com.olgabakhur.baseproject.presentation.util.view.gone
import com.olgabakhur.baseproject.presentation.util.view.visible
import com.olgabakhur.baseproject.presentation.util.viewmodel.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.rootMainActivity)
    private val viewModel: MainViewModel by viewModel { App.appComponent.mainViewModel }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var destinationChangeListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupOrientationChange()
        initNavController()
        setupToolbarNavigation()
        setupAppBarsVisibility()
        setupSystemBackButton()
    }

    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onStop() {
        super.onStop()
        navController.removeOnDestinationChangedListener(destinationChangeListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun observeViewModel() {
        /* Only for generic errors */
        collectWhenCreated(viewModel.getApplicationErrors()) { error ->
            showOkDialogWithTitle(
                this,
                R.string.general_error,
                error.message(this)
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
                R.id.breakingNewsFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupAppBarsVisibility() {
        destinationChangeListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                setupToolbarVisibility(destination.id)
            }
    }

    private fun setupToolbarVisibility(destinationId: Int) {
        when (destinationId) {
//            R.id.signInFragment -> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
    }

    private fun setupSystemBackButton() {
        onBackPressedDispatcher.addCallback(this) {
            when (navController.getCurrentDestinationId()) {
                R.id.breakingNewsFragment -> finish()
                else -> navController.navigateUp()
            }
        }
    }
}