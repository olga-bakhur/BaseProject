package com.olgabakhur.baseproject.presentation.util.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Keyboard {

    fun hideKeyboard(view: View, context: Context) {
        getInputMethodManager(context).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}

