package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.olgabakhur.baseproject.R

object Dialog {

    fun showOkDialog(
        context: Context,
        @StringRes title: Int,
        message: String,
        listener: (() -> Unit)? = null
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
                listener?.invoke()
                dialog.dismiss()
            }
            .show()
    }

    fun showOkCancelDialog(
        context: Context,
        @StringRes title: Int,
        message: String,
        listenerButtonOk: (() -> Unit)? = null
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
                listenerButtonOk?.invoke()
                dialog.dismiss()
            }
            .setNeutralButton(context.resources.getString(R.string.general_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}