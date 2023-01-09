package com.olgabakhur.baseproject.presentation.extensions

import android.content.Context
import com.olgabakhur.baseproject.R

fun Context.getTextOrNoInfoMessage(text: String?): String =
    if (text.isNullOrBlank()) {
        resources.getString(R.string.general_no_info)
    } else {
        text
    }
