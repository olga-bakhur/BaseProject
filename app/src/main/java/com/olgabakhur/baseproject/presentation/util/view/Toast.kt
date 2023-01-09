package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object Toast {

    fun showToast(
        context: Context,
        @StringRes messageStringRes: Int,
        length: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, messageStringRes, length).show()
    }
}