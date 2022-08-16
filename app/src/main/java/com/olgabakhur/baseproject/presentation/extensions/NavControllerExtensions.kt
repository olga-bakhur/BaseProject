package com.olgabakhur.baseproject.presentation.extensions

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigate(@IdRes resId: Int) = navigate(resId)

fun NavController.navigate(direction: NavDirections) = navigate(direction)

fun NavController.navigate(direction: NavDirections, navOptions: NavOptions) =
    navigate(direction, navOptions)

fun NavController.navigateBack() = popBackStack()