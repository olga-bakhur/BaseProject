package com.olgabakhur.baseproject.presentation.util.view

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.select() {
    this.isSelected = true
}

fun View.deselect() {
    this.isSelected = false
}

fun View.activate() {
    this.isActivated = true
}

fun View.deactivate() {
    this.isActivated = false
}