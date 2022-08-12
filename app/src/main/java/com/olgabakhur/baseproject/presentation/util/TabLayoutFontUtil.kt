package com.olgabakhur.baseproject.presentation.util

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.olgabakhur.baseproject.presentation.extensions.disable

fun changeSelectedTabFont(
    tabLayout: TabLayout,
    tabPosition: Int,
    @FontRes fontFamilyRes: Int,
    @ColorRes colorId: Int,
    context: Context
) {
    val linearLayout =
        (tabLayout.getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
    val tabTextView = linearLayout.getChildAt(1) as TextView
    val typeface = ResourcesCompat.getFont(context, fontFamilyRes)
    tabTextView.typeface = typeface
    tabTextView.setTextColor(context.resources.getColor(colorId, null))
}

fun disableTab(
    tabLayout: TabLayout,
    tabPosition: Int,
    @FontRes fontFamilyRes: Int,
    @ColorRes colorId: Int,
    context: Context
) {
    tabLayout.getTabAt(tabPosition)?.view?.disable()
    changeSelectedTabFont(tabLayout, tabPosition, fontFamilyRes, colorId, context)
}