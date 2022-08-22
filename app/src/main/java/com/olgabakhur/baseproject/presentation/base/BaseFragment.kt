package com.olgabakhur.baseproject.presentation.base

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
import androidx.viewbinding.ViewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.olgabakhur.baseproject.presentation.util.liveData.EventObserver
import com.olgabakhur.baseproject.presentation.util.view.createProgressBar
import com.olgabakhur.baseproject.presentation.util.view.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel
    abstract val binding: ViewBinding

    private lateinit var mContext: Context
    private var progressBar: CircularProgressIndicator? = null

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

    /*
    Override this function in a particular subFragment to add more MenuItems or clear items
    which are common for the whole App.
    */
    open fun setupToolbarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    open fun observeViewModel() {
        viewModel.loading.observe(
            viewLifecycleOwner,
            EventObserver { isLoading ->
                showLoading(isLoading)
                blockUi(isLoading)
            }
        )
    }

    /* Loading */
    fun showLoading(isLoading: Boolean) {
        progressBar = if (isLoading) {
            createProgressBar(mContext, binding.root).apply { show() }
        } else {
            progressBar?.hide().let { null }
        }
    }

    private fun blockUi(isLoading: Boolean) {
        if (isLoading) disableUi() else enableUi()
    }

    /* Override those functions in a particular subFragment to enable / disable all the clickable views. */
    open fun disableUi() {}
    open fun enableUi() {}

    /* Navigation */
    fun navigate(@IdRes resId: Int) = findNavController().navigate(resId)

    fun navigate(direction: NavDirections) = findNavController().navigate(direction)

    fun navigate(direction: NavDirections, navOptions: NavOptions) =
        findNavController().navigate(direction, navOptions)

    fun navigateBack() = findNavController().popBackStack()

    fun navigateUp() = findNavController().navigateUp()
}