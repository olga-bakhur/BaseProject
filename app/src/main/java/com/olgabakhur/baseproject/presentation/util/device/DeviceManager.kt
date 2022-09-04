package com.olgabakhur.baseproject.presentation.util.device

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import com.olgabakhur.baseproject.App
import kotlin.math.pow
import kotlin.math.sqrt

object DeviceManager {

    @Suppress("DEPRECATION") // TODO: update deprecated functions
    fun getDeviceType(): DeviceType {
        val vm: WindowManager = App.application
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        vm.defaultDisplay.getRealMetrics(dm)
        val point = Point()
        vm.defaultDisplay.getRealSize(point)
        val x = (point.x / dm.xdpi).pow(2.0F)
        val y = (point.y / dm.ydpi).pow(2.0F)
        val screenInches = sqrt(x + y)

        if (screenInches > 13.9F) {
            return DeviceType.TV
        }

        if (screenInches > 6.9F) {
            return DeviceType.Tablet
        }

        return DeviceType.Phone
    }
}

enum class DeviceType {
    TV,
    Tablet,
    Phone
}