package com.example.omdb.util.extensions

import androidx.navigation.NavController

/**
 * navigates to route while popping up to passed screen with inclusive flag
 *
 * @param route route for the destination
 * @param popUpTo route for the pop destination
 * @param inclusive whether the popUpTo destination should be popped from the back stack
 */
fun NavController.navigateWithPopUpTo(
    route: String,
    popUpTo: String,
    inclusive: Boolean = true
) {
    navigate(route = route) {
        popUpTo(route = popUpTo) {
            this.inclusive = inclusive
        }
    }
}