package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/* Simple Snackbar */
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
    Snackbar.make(view, context.resources.getString(messageStringRes), length).show()
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
    Snackbar.make(view, messageString, length).show()
}


/* Snackbar with action */
fun showSnackbarWithAction(
    context: Context,
    view: View,
    @StringRes messageStringRes: Int,
    length: Int = Snackbar.LENGTH_SHORT,
    action: SnackbarAction
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)
    Snackbar.make(view, context.getString(messageStringRes), length).apply {
        setAction(context.getString(action.actionTitleStringRes)) {
            action.doAction()
        }
        show()
    }
}

fun showSnackbarWithAction(
    context: Context,
    view: View,
    messageString: String,
    length: Int = Snackbar.LENGTH_SHORT,
    action: SnackbarAction
) {
    if (!view.isAttachedToWindow) {
        return
    }

    context.hideKeyboard(view)
    Snackbar.make(view, messageString, length).apply {
        setAction(context.getString(action.actionTitleStringRes)) {
            action.doAction()
        }
        show()
    }
}

/* Snackbar with a custom background */
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
    Snackbar.make(view, context.resources.getString(messageStringRes), length).apply {
        view.setBackgroundColor(context.resources.getColor(backgroundColor, null))
        show()
    }
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
    Snackbar.make(view, messageString, length).apply {
        view.setBackgroundColor(context.resources.getColor(backgroundColor, null))
        show()
    }
}

class SnackbarAction(
    val doAction: () -> Unit,
    @StringRes val actionTitleStringRes: Int
)