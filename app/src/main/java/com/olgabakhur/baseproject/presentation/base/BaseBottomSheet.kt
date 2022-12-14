@file:Suppress(
    "EmptyMethod", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate"
)

package com.olgabakhur.baseproject.presentation.base

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.olgabakhur.baseproject.presentation.util.livedata.EventObserver
import com.olgabakhur.baseproject.presentation.util.view.Keyboard
import com.olgabakhur.baseproject.presentation.util.view.ProgressBar

abstract class BaseBottomSheet(@LayoutRes val layoutId: Int) : BottomSheetDialogFragment() {

    abstract val viewModel: BaseViewModel
    abstract val binding: ViewBinding

    private lateinit var mContext: Context
    private var progressBar: CircularProgressIndicator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        observeViewModel()
    }

    override fun onPause() {
        super.onPause()
        view?.let { Keyboard.hideKeyboard(it, mContext) }
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
            progressBar = ProgressBar.createProgressBar(mContext, binding.root)
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext())

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupSize(it)
                behaviour.apply {
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    isHideable = true
                    skipCollapsed = true
                }
            }
        }

        return dialog
    }

    open fun setupSize(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            layoutParams.width = resources.displayMetrics.widthPixels / 3
        }
        bottomSheet.layoutParams = layoutParams
    }
}