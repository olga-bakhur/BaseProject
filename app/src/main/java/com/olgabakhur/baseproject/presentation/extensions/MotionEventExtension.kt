package com.olgabakhur.baseproject.presentation.extensions

import android.graphics.Rect
import android.view.MotionEvent
import kotlin.math.pow

fun MotionEvent.isInRadius(bounds: Rect): Boolean {
    val radius = (bounds.bottom - bounds.top) / 2.0F
    return radius.pow(2) >= (this.x - bounds.exactCenterX())
        .pow(2) + (this.y - bounds.exactCenterY()).pow(2)
}

