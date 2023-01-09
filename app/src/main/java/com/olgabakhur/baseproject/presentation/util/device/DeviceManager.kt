package com.olgabakhur.baseproject.presentation.util.device

import android.content.Context
import kotlin.math.pow
import kotlin.math.sqrt

object DeviceManager {

    fun getDeviceType(context: Context): DeviceType {
        val displayMetrics = context.resources.displayMetrics
        val density = (displayMetrics.density * 160).toDouble()
        val width = (displayMetrics.widthPixels / density).pow(2.0)
        val height = (displayMetrics.heightPixels / density).pow(2.0)
        val screenInches = sqrt(width + height)

        return when {
            screenInches > 13.9F -> DeviceType.TV
            screenInches > 6.9F -> DeviceType.Tablet
            else -> DeviceType.Phone
        }
    }
}

enum class DeviceType {
    TV,
    Tablet,
    Phone
}