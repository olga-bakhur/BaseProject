package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToast(
    context: Context,
    @StringRes messageStringRes: Int,
    length: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, messageStringRes, length).show()
}

fun showToast(
    context: Context,
    messageString: String,
    length: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, messageString, length).show()
}