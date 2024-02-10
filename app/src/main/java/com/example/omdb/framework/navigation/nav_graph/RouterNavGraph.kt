package com.example.omdb.framework.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.omdb.framework.navigation.Route
import com.example.omdb.framework.navigation.Screen
import com.example.omdb.ui.router.RouterScreen
import com.example.omdb.util.extensions.navigateWithPopUpTo

fun NavGraphBuilder.routerNavGraph(navController: NavController) {
    navigation(
        route = Route.Router.name,
        startDestination = Screen.Router.route
    ) {
        composable(
            route = Screen.Router.route
        ) {
            RouterScreen(
                navigateRouterToSignUp = {
                    navController.navigateWithPopUpTo(
                        route = Route.Auth.name,
                        popUpTo = Route.Router.name
                    )
                },
                navigateRouterToHome = {
                    navController.navigateWithPopUpTo(
                        route = Route.App.name,
                        popUpTo = Route.Router.name
                    )
                }
            )
        }
    }
}