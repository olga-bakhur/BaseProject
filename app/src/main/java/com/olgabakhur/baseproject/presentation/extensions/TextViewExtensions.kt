package com.olgabakhur.baseproject.presentation.extensions

import android.widget.TextView
import com.olgabakhur.baseproject.R

fun TextView.setTextOrDefaultMessage(text: String?, defaultMessage: String? = null) {
    this.text = if (text.isNullOrBlank()) {
        defaultMessage ?: resources.getString(R.string.general_no_info)
    } else {
        text
    }
}