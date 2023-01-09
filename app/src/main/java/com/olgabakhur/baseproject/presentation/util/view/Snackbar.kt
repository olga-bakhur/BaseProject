package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

object Snackbar {

    fun showSnackbar(
        context: Context,
        view: View,
        messageString: String,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        if (!view.isAttachedToWindow) {
            return
        }

        Keyboard.hideKeyboard(view, context)
        Snackbar.make(view, messageString, length).show()
    }
}

