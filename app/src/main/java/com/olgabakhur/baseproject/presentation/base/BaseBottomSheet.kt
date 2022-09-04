package com.olgabakhur.baseproject.presentation.base

import android.app.Dialog
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.olgabakhur.baseproject.R

abstract class BaseBottomSheet(
    @LayoutRes val layoutId: Int,
    @StyleRes val themeBottomSheet: Int? = null
) : BottomSheetDialogFragment() {

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(
            requireContext(),
            themeBottomSheet ?: R.style.BottomSheet_SystemBarsStyle
        )

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

    private fun setupSize(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            layoutParams.width = resources.displayMetrics.widthPixels / 3
        }
        bottomSheet.layoutParams = layoutParams
    }
}