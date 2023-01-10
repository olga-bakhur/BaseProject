package com.olgabakhur.baseproject.presentation.util.recyclerView

import android.content.Context

object RecyclerViewUtil {

    fun getColumnCountByScreenWidth(context: Context): Int {
        val configuration = context.resources.configuration
        val columnCount = (configuration.screenWidthDp / configuration.fontScale / 175).toInt()
        if (columnCount < 2) {
            return 2
        }
        return columnCount
    }
}