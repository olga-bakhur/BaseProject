package com.olgabakhur.baseproject.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.view.Dialog.showOkDialogWithTitle
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.rootMainActivity)
    private val viewModel: MainViewModel by viewModel { App.appComponent.mainViewModel }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        observeViewModel()
        initNavController()
        setupBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val destination = navController.findDestination(item.itemId)

        return if (destination != null) {
            navController.navigate(item.itemId)
            true
        } else {
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun observeViewModel() {
        // TODO: only for general errors
        collectWhenCreated(viewModel.getApplicationErrors()) { error ->
            showOkDialogWithTitle(
                mContext,
                R.string.general_error,
                error.message(mContext)
            )
        }
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation() {
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.breakingNewsFragment,
                R.id.savedNewsFragment,
                R.id.searchNewsFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}