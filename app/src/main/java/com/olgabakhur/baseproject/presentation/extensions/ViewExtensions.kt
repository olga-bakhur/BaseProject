/*
 * ┌────────────────────────────┐
 * │                            │
 * │   (C) 2014-2021 iHaus AG   │
 * │                            │
 * └────────────────────────────┘
 */

package com.olgabakhur.baseproject.presentation.extensions

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
