package com.olgabakhur.baseproject.presentation.extensions

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout

fun TabLayout.setupDotIndicatorsColor(@DrawableRes selectorDrawableRes: Int) {
    for (i in 0 until this.tabCount) {
        this.getTabAt(i)?.view?.let { tabView ->
            tabView.background = ResourcesCompat.getDrawable(
                resources,
                selectorDrawableRes,
                null
            )
        }
    }
}

fun TabLayout.changeSelectedTabFont(
    tabPosition: Int,
    @FontRes fontFamilyRes: Int,
    @ColorRes colorId: Int
) {
    val linearLayout =
        (this.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
    val tabTextView = linearLayout.getChildAt(1) as TextView
    val typeface = ResourcesCompat.getFont(context, fontFamilyRes)
    tabTextView.typeface = typeface
    tabTextView.setTextColor(context.resources.getColor(colorId, null))
}