package com.olgabakhur.baseproject.presentation.util.recyclerView

import android.content.Context

object RecyclerViewUtil {

    fun getDeviceColumnCount(context: Context): Int {
        val cfg = context.resources.configuration
        val count = (cfg.screenWidthDp / cfg.fontScale / 175).toInt()
        if (count < 2) {
            return 2
        }
        return count
    }
}