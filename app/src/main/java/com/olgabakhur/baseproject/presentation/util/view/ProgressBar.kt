package com.olgabakhur.baseproject.presentation.util.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.olgabakhur.baseproject.R

/* Only for ConstraintLayout */
fun createProgressBar(context: Context, rootView: View): CircularProgressIndicator {
    val progressBar = CircularProgressIndicator(context).apply {
        isIndeterminate = true
        elevation = resources.getDimension(R.dimen.dimen_5)
    }

    val lp = ConstraintLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        startToStart = ConstraintSet.PARENT_ID
        topToTop = ConstraintSet.PARENT_ID
        endToEnd = ConstraintSet.PARENT_ID
        bottomToBottom = ConstraintSet.PARENT_ID
    }

    (rootView as ViewGroup).addView(progressBar, lp)
    return progressBar
}