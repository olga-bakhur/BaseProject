package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.olgabakhur.baseproject.R

object Dialog {

    /* "Ok" dialog with title */
    fun showOkDialogWithTitle(
        context: Context,
        @StringRes title: Int,
        @StringRes message: Int,
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

    fun showOkDialogWithTitle(
        context: Context,
        title: String,
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

    fun showOkDialogWithTitle(
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

    fun showOkDialogWithTitle(
        context: Context,
        title: String,
        @StringRes message: Int,
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

    /* "Ok" dialog without title */
    fun showOkDialogNoTitle(
        context: Context,
        @StringRes message: Int,
        listener: (() -> Unit)? = null
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
                listener?.invoke()
                dialog.dismiss()
            }
            .show()
    }

    fun showOkDialogNoTitle(
        context: Context,
        message: String,
        listener: (() -> Unit)? = null
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
                listener?.invoke()
                dialog.dismiss()
            }
            .show()
    }

    /* "Ok / Cancel" dialog with title */
    fun showOkCancelDialogWithTitle(
        context: Context,
        @StringRes title: Int,
        @StringRes message: Int,
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
            .setNegativeButton(context.resources.getString(R.string.general_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showOkCancelDialogWithTitle(
        context: Context,
        title: String,
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
            .setNegativeButton(context.resources.getString(R.string.general_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showOkCancelDialogWithTitle(
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
            .setNegativeButton(context.resources.getString(R.string.general_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showOkCancelDialogWithTitle(
        context: Context,
        title: String,
        @StringRes message: Int,
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
            .setNegativeButton(context.resources.getString(R.string.general_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}