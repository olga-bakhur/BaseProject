package com.olgabakhur.baseproject.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.ui.MainActivity
import com.olgabakhur.baseproject.presentation.util.view.Keyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel
    abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        view?.let { Keyboard.hideKeyboard(it, requireContext()) }
    }

    open fun observeViewModel() {
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.loading) { isLoading ->
            showLoading(isLoading)
            blockUi(isLoading)
        }
    }

    /* Loading */
    fun showLoading(isLoading: Boolean) {
        activity?.let { hostActivity ->
            if (hostActivity is MainActivity) {
                hostActivity.showLoading(isLoading)
            }
        }
    }

    private fun blockUi(isLoading: Boolean) {
        if (isLoading) disableUi() else enableUi()
    }

    open fun disableUi() {}
    open fun enableUi() {}

    /* Navigation */
    fun navigate(@IdRes resId: Int) = findNavController().navigate(resId)

    fun navigate(direction: NavDirections) = findNavController().navigate(direction)

    fun navigate(direction: NavDirections, navOptions: NavOptions) =
        findNavController().navigate(direction, navOptions)

    fun navigateBack(@IdRes destinationId: Int? = null, inclusive: Boolean? = null) {
        if (destinationId != null && inclusive != null) {
            findNavController().popBackStack(destinationId, inclusive)
        } else {
            findNavController().popBackStack()
        }
    }

    fun navigateUp() = findNavController().navigateUp()
}