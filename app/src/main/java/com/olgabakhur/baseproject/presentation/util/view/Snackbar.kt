@file:SuppressLint("WrongConstant")

package com.olgabakhur.baseproject.presentation.util.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


// Simple Snackbar
fun showSnackbar(
    context: Context,
    view: View,
    @StringRes messageStringRes: Int,
    length: Int = Snackbar.LENGTH_SHORT
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbar(
        view = view,
        message = context.resources.getString(messageStringRes),
        length = length
    )
}

fun showSnackbar(
    context: Context,
    view: View,
    messageString: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbar(
        view = view,
        message = messageString,
        length = length
    )
}

private fun createSnackbar(
    view: View,
    message: String,
    length: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(view, message, length).show()
}

// Snackbar with action
fun showSnackbarWithAction(
    context: Context,
    view: View,
    @StringRes messageStringRes: Int,
    length: Int = Snackbar.LENGTH_SHORT,
    snackbarAction: SnackbarAction
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbarWithAction(
        view = view,
        message = context.getString(messageStringRes),
        length = length,
        snackbarAction = snackbarAction,
        context = context
    )
}

fun showSnackbarWithAction(
    context: Context,
    view: View,
    messageString: String,
    length: Int = Snackbar.LENGTH_SHORT,
    snackbarAction: SnackbarAction
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbarWithAction(
        view = view,
        message = messageString,
        length = length,
        snackbarAction = snackbarAction,
        context = context
    )
}

private fun createSnackbarWithAction(
    view: View,
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    snackbarAction: SnackbarAction? = null,
    context: Context
) {
    snackbarAction?.let { action ->
        Snackbar.make(view, message, length).apply {
            setAction(context.getString(action.actionTitleStringRes)) {
                action.doAction()
            }
            show()
        }
    }
}

// Snackbar with a custom background
fun showSnackbarWithCustomBackground(
    context: Context,
    view: View,
    @StringRes messageStringRes: Int,
    length: Int = Snackbar.LENGTH_SHORT,
    @ColorRes backgroundColor: Int
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbarWithCustomBackground(
        view = view,
        message = context.resources.getString(messageStringRes),
        length = length,
        backgroundColor = backgroundColor,
        context = context
    )
}

fun showSnackbarWithCustomBackground(
    context: Context,
    view: View,
    messageString: String,
    length: Int = Snackbar.LENGTH_SHORT,
    @ColorRes backgroundColor: Int
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)

    createSnackbarWithCustomBackground(
        view = view,
        message = messageString,
        length = length,
        backgroundColor = backgroundColor,
        context = context
    )
}

private fun createSnackbarWithCustomBackground(
    view: View,
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    @ColorRes backgroundColor: Int?,
    context: Context
) {
    backgroundColor?.let {
        Snackbar.make(view, message, length).apply {
            view.setBackgroundColor(context.resources.getColor(backgroundColor, null))
            show()
        }
    }
}

class SnackbarAction(
    val doAction: () -> Unit,
    @StringRes val actionTitleStringRes: Int
)