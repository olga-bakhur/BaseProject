package com.olgabakhur.baseproject.presentation.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.olgabakhur.baseproject.presentation.util.liveData.EventObserver
import com.olgabakhur.baseproject.presentation.util.view.buildLoadingDialog
import com.olgabakhur.baseproject.presentation.util.view.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

    private var loadingDialog: Dialog? = null
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        setupToolbarMenu()
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        view?.let { mContext.hideKeyboard(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.loading.removeObservers(viewLifecycleOwner)
    }

    private fun setupToolbarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    open fun observeViewModel() {
        viewModel.loading.observe(
            viewLifecycleOwner,
            EventObserver {
                showLoading(it)
            })
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog = if (isLoading) {
            buildLoadingDialog().apply { show() }
        } else {
            loadingDialog?.dismiss().let { null }
        }
    }

    /* Navigation */
    fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun navigate(direction: NavDirections, navOptions: NavOptions) {
        findNavController().navigate(direction, navOptions)
    }

    fun navigateBack() {
        findNavController().popBackStack()
    }
}