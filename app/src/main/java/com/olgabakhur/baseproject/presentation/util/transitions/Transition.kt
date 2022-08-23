package com.olgabakhur.baseproject.presentation.util.transitions

import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.presentation.util.view.gone
import com.olgabakhur.baseproject.presentation.util.view.visible

fun createFadeTransition(
    screenRoot: ViewGroup,
    targetView: View,
    shouldHide: Boolean
) {
    val mContext = targetView.context

    val materialFade = MaterialFade().apply {
        duration = mContext.resources.getInteger(
            R.integer.transition_duration_view_fade
        ).toLong()
    }

    TransitionManager.beginDelayedTransition(screenRoot, materialFade)

    targetView.apply {
        if (shouldHide) gone() else visible()
    }
}

fun createFadeThroughTransition(
    screenRoot: ViewGroup,
    outgoingView: View,
    incomingView: View
) {
    val fadeThrough = MaterialFadeThrough()

    TransitionManager.beginDelayedTransition(screenRoot, fadeThrough)

    outgoingView.gone()
    incomingView.visible()
}