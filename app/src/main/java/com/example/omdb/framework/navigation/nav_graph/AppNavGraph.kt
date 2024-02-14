package com.example.omdb.framework.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.omdb.domain.model.Type
import com.example.omdb.framework.navigation.Route
import com.example.omdb.framework.navigation.Screen
import com.example.omdb.ui.details.DetailsScreen
import com.example.omdb.ui.details.episodes.EpisodesScreen
import com.example.omdb.ui.details.full_poster.FullPosterScreen
import com.example.omdb.ui.details.ratings.RatingsScreen
import com.example.omdb.ui.details.team_details.TeamDetailsScreen
import com.example.omdb.ui.home.HomeScreen
import com.example.omdb.ui.search.SearchScreen

fun NavGraphBuilder.appNavGraph(navController: NavController) {
    navigation(
        route = Route.App.name,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            arguments = Screen.Home.arguments
        ) {
            HomeScreen(
                navigateHomeToSearchMovies = {
                    navController.navigate(
                        route = Screen.Search.getPath(category = Type.MOVIE)
                    )
                },
                navigateHomeToSearchSeries = {
                    navController.navigate(
                        route = Screen.Search.getPath(category = Type.SERIES)
                    )
                }
            )
        }
        composable(
            route = Screen.Search.getPath(),
            arguments = Screen.Search.arguments
        ) {
            SearchScreen(
                navigateSearchToDetails = {
                    navController.navigate(
                        route = Screen.Details.getPath(shortContent = it)
                    )
                }
            )
        }
        composable(
            route = Screen.Details.getPath(),
            arguments = Screen.Details.arguments
        ) {
            DetailsScreen()
        }
        composable(
            route = Screen.FullPoster.route,
            arguments = Screen.FullPoster.arguments
        ) {
            FullPosterScreen()
        }
        composable(
            route = Screen.Ratings.route,
            arguments = Screen.Ratings.arguments
        ) {
            RatingsScreen()
        }
        composable(
            route = Screen.TeamDetails.route,
            arguments = Screen.TeamDetails.arguments
        ) {
            TeamDetailsScreen()
        }
        composable(
            route = Screen.Episodes.route,
            arguments = Screen.Episodes.arguments
        ) {
            EpisodesScreen()
        }
    }
}