package com.olgabakhur.baseproject.presentation.util

import android.view.View

class DebouncingOnClickListener(
    private val intervalMillis: Long,
    private val doClick: ((View) -> Unit)
) : View.OnClickListener {

    override fun onClick(v: View) {
        if (enabled || isTesting) {
            enabled = false
            v.postDelayed(ENABLE_AGAIN, intervalMillis)
            doClick(v)
        }
    }

    companion object {
        @JvmStatic
        var isTesting = false
        @JvmStatic
        var enabled = true
        private val ENABLE_AGAIN =
            Runnable { enabled = true }
    }
}


fun View.setOnCLick(intervalMillis: Long = 0, doClick: (View) -> Unit) =
    setOnClickListener(DebouncingOnClickListener(intervalMillis = intervalMillis, doClick = doClick))