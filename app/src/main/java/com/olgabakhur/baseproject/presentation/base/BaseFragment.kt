package com.olgabakhur.baseproject.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.olgabakhur.baseproject.presentation.util.livedata.EventObserver
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
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        view?.let { mContext.hideKeyboard(it) }
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
        if (progressBar == null) {
            progressBar = createProgressBar(mContext, binding.root)
        }

        if (isLoading) {
            progressBar?.show()
        } else {
            progressBar?.hide()
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