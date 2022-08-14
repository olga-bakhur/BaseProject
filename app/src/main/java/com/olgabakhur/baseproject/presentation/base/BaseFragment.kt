package com.olgabakhur.baseproject.presentation.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import com.olgabakhur.baseproject.presentation.util.EventObserver

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

    private var loadingDialog: Dialog? = null

//    private var activeToast: Toast? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    open fun observeViewModel() {
        viewModel.loading.observe(
            viewLifecycleOwner,
            EventObserver {
                showLoading(it)
            })
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.loading.removeObservers(viewLifecycleOwner)
    }

    /* Messages */



    //        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

//    fun toast(message: String) {
//        activeToast?.cancel()
//        activeToast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
//        activeToast!!.show()
//    }


    /* Loadings */
    protected fun showLoading(isLoading: Boolean) {
//        loadingDialog = if (isLoading) {
//            LoadingDialog.buildLoadingDialog(this.view).apply { show() }
//        } else {
//            loadingDialog?.dismiss().let { null }
//        }
    }

//    protected fun getRecyclerHorizontalMargin(): Int {
//        val marginPercent =
//            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.05 else 0.03
//        val dm = resources.displayMetrics
//        val extraMargin = resources.getDimensionPixelSize(R.dimen.margin_content_horizontal)
//        return (dm.widthPixels * marginPercent + extraMargin).toInt()
//    }

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

    /* Other */
    private fun hideKeyboard() {
        view?.let { rootView ->
            activity?.let {
                val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rootView.windowToken, 0)
            }
        }
    }
}