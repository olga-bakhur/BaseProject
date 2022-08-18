package com.olgabakhur.baseproject.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.ActivityMainBinding
import com.olgabakhur.baseproject.presentation.extensions.collectWhenCreated
import com.olgabakhur.baseproject.presentation.extensions.getCurrentDestinationId
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.view.Dialog.showOkDialogWithTitle
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.rootMainActivity)
    private val viewModel: MainViewModel by viewModel { App.appComponent.mainViewModel }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        initNavController()
        setupToolbarNavigation()
        setupToolbarMenu()
        setupSystemBackButton()
    }

    private fun setupToolbarMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                navController.getCurrentDestinationId()?.let { destinationId ->
                    when (destinationId) {
                        R.id.signInFragment -> menu.clear()
                        else -> menuInflater.inflate(R.menu.menu_toolbar, menu)
                        /* Inflate other menus here if necessary */
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.actionSignOut -> {
                        viewModel.signOutFake {
                            navController.popBackStack(R.id.signInFragment, false)
                        }
                        true
                    }

                    R.id.savedNewsFragment -> {
                        navController.navigate(R.id.savedNewsFragment)
                        true
                    }
                    else -> false
                }

        }, this)
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

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupToolbarNavigation() {
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.signInFragment,
                R.id.breakingNewsFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupSystemBackButton() {
        onBackPressedDispatcher.addCallback(this) {
            when (navController.getCurrentDestinationId()) {
                R.id.signInFragment,
                R.id.breakingNewsFragment -> finish()
                else -> navController.navigateUp()
            }
        }
    }
}