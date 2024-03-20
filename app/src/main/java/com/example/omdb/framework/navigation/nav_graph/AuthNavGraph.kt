package com.example.omdb.framework.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.omdb.framework.navigation.Route
import com.example.omdb.framework.navigation.Screen
import com.example.omdb.ui.authentication.SignUpScreen
import com.example.omdb.util.anim.slideInBottom
import com.example.omdb.util.anim.slideInTop
import com.example.omdb.util.anim.slideOutBottom
import com.example.omdb.util.anim.slideOutTop
import com.example.omdb.util.extensions.navigateWithPopUpTo

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Route.Auth.name,
        startDestination = Screen.SignUp.route
    ) {
        composable(
            route = Screen.SignUp.route,
            arguments = Screen.SignUp.arguments,
            enterTransition = { slideInBottom() },
            exitTransition = { slideOutTop() },
            popEnterTransition = { slideInTop() },
            popExitTransition = { slideOutBottom() }
        ) {
            SignUpScreen(
                navigateSignUpToHome = {
                    navController.navigateWithPopUpTo(
                        route = Route.App.name,
                        popUpTo = Route.Auth.name
                    )
                }
            )
        }
    }
}