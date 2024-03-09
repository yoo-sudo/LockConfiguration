package com.example.locksettingconfiguration.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

fun Fragment.findNavControllerSafely(): NavController? = if (isAdded) findNavController() else null

fun NavController.navigateTo(currentDestination: Int, navigateActionId: Int) {
    if (this.currentDestination?.id == currentDestination) navigate(navigateActionId)
}

fun NavController.navigateBack(currentDestination: Int, inclusive: Boolean, saveState: Boolean) {
    if (this.currentDestination?.id == currentDestination)
        popBackStack(this.currentDestination!!.id, inclusive, saveState)
}

fun Fragment.navigateTo(currentDestination: Int, navigateActionId: Int) {
    findNavControllerSafely()?.navigateTo(currentDestination, navigateActionId)
}

fun Fragment.navigateBack(currentDestination: Int, inclusive: Boolean, saveState: Boolean = false) {
    findNavControllerSafely()?.navigateBack(currentDestination, inclusive, saveState)
}
