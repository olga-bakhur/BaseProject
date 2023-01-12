package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.olgabakhur.baseproject.R

object ProgressBar {

    fun createProgressBar(
        context: Context,
        rootView: View
    ): CircularProgressIndicator {
        val progressBar = CircularProgressIndicator(context).apply {
            isIndeterminate = true
            elevation = resources.getDimension(R.dimen.progress_bar_radius)
        }
        val lpProgressBar = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )

        val frameLayout = FrameLayout(context)
        val lpFrameLayout = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        frameLayout.addView(progressBar, lpProgressBar)
        (rootView as ViewGroup).addView(frameLayout, lpFrameLayout)

        return progressBar
    }
}
