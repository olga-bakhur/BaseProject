package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes messageStringRes: Int) {

    Toast.makeText(this, messageStringRes, Toast.LENGTH_SHORT).show()
}
