package com.olgabakhur.baseproject.presentation.extensions

import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.hideErrorOnTextChanged() {
    this.editText?.doOnTextChanged { _, _, _, _ ->
        this.error = null
    }
}