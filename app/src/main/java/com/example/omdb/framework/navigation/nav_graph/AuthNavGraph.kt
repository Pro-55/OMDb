package com.example.omdb.framework.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.omdb.framework.navigation.Route
import com.example.omdb.framework.navigation.Screen
import com.example.omdb.ui.authentication.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Route.Auth.name,
        startDestination = Screen.SignUp.route
    ) {
        composable(
            route = Screen.SignUp.route,
            arguments = Screen.SignUp.arguments
        ) {
            SignUpScreen()
        }
    }
}