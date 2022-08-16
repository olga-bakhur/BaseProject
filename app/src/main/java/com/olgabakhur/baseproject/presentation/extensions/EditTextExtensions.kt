package com.olgabakhur.baseproject.presentation.extensions

import android.widget.EditText

fun EditText.isNotBlankInputField() = !text?.toString().isNullOrBlank()