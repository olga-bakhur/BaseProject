//package com.olgabakhur.baseproject.presentation.util
//
//import android.app.Dialog
//import android.content.Context
//import androidx.annotation.StringRes
//import androidx.appcompat.app.AlertDialog
//import androidx.lifecycle.LifecycleOwner
//
//import com.google.android.material.dialog.MaterialAlertDialogBuilder
//import com.olgabakhur.baseproject.R
//
//
//class DialogUtil {
//    companion object {
//        fun showOneOptionDialog(
//            owner: LifecycleOwner,
//            context: Context,
//            @StringRes title: Int,
//            @StringRes message: Int
//        ): Dialog {
//            return MaterialDialog(context)
//                .lifecycleOwner(owner)
//                .title(title)
//                .message(message)
//                .cornerRadius(res = R.dimen.corner_radius_12)
//                .cancelOnTouchOutside(cancelable = false)
//                .positiveButton(res = R.string.general_ok, null) {
//                    it.dismiss()
//                }
//        }
//
//        fun showOneOptionDialog(
//            context: Context,
//            @StringRes title: Int,
//            @StringRes message: Int,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showOneOptionDialog(context: Context, @StringRes title: Int, message: String) {
//            MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showOneOptionDialog(
//            context: Context,
//            title: String,
//            message: String,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showOneOptionDialog(
//            context: Context,
//            @StringRes title: Int,
//            message: String,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showOneOptionDialogNoTitle(
//            context: Context,
//            @StringRes message: Int,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showTwoOptionsDialogWithTitle(
//            context: Context,
//            @StringRes title: Int,
//            @StringRes message: Int,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .setNegativeButton(context.resources.getString(R.string.cancel)) { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun showOneOptionDialogNoTitle(
//            context: Context,
//            message: String,
//            listener: (() -> Unit)? = null
//        ): AlertDialog {
//            return MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.general_ok)) { dialog, _ ->
//                    listener?.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        fun askBiometricDialog(
//            context: Context,
//            @StringRes title: Int,
//            @StringRes message: Int,
//            positiveListener: () -> Unit,
//            negativeListener: () -> Unit
//        ) {
//            MaterialAlertDialogBuilder(context, R.style.DialogStyle_RoundedCorners)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton(context.resources.getString(R.string.yes)) { dialog, _ ->
//                    positiveListener.invoke()
//                    dialog.dismiss()
//                }
//                .setNegativeButton(context.resources.getString(R.string.no)) { dialog, _ ->
//                    negativeListener.invoke()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//    }
//}