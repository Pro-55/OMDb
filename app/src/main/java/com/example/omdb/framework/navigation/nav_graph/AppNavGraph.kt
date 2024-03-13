package com.example.omdb.framework.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.omdb.domain.model.ShortContent
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
import com.example.omdb.util.anim.slideInEnd
import com.example.omdb.util.anim.slideInStart
import com.example.omdb.util.anim.slideOutEnd
import com.example.omdb.util.anim.slideOutStart

fun NavGraphBuilder.appNavGraph(
    navController: NavController,
    onBack: () -> Unit
) {
    navigation(
        route = Route.App.name,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            arguments = Screen.Home.arguments,
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
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
            arguments = Screen.Search.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
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
            arguments = Screen.Details.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
        ) {
            DetailsScreen(
                onBack = onBack,
                navigateDetailsToFullPoster = {
                    navController.navigate(
                        route = Screen.FullPoster.getPath(posterUrl = it ?: "")
                    )
                },
                navigateDetailsToRatings = {
                    navController.navigate(
                        route = Screen.Ratings.getPath(ratings = it)
                    )
                },
                navigateDetailsToTeamDetails = {
                    navController.navigate(
                        route = Screen.TeamDetails.getPath(team = it)
                    )
                },
                navigateDetailsToEpisodes = { contentId, season ->
                    navController.navigate(
                        route = Screen.Episodes.getPath(
                            contentId = contentId,
                            season = season
                        )
                    )
                }
            )
        }
        composable(
            route = Screen.FullPoster.getPath(),
            arguments = Screen.FullPoster.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
        ) {
            FullPosterScreen(onBack = onBack)
        }
        composable(
            route = Screen.Ratings.getPath(),
            arguments = Screen.Ratings.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
        ) {
            RatingsScreen(onBack = onBack)
        }
        composable(
            route = Screen.TeamDetails.getPath(),
            arguments = Screen.TeamDetails.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
        ) {
            TeamDetailsScreen(onBack = onBack)
        }
        composable(
            route = Screen.Episodes.getPath(),
            arguments = Screen.Episodes.arguments,
            enterTransition = { slideInEnd() },
            exitTransition = { slideOutStart() },
            popEnterTransition = { slideInStart() },
            popExitTransition = { slideOutEnd() }
        ) {
            EpisodesScreen(
                onBack = onBack,
                navigateEpisodesToDetails = {
                    navController.navigate(
                        route = Screen.Details.getPath(
                            shortContent = ShortContent(
                                _id = it._id,
                                title = it.title,
                                year = it.released,
                                poster = null
                            )
                        )
                    )
                }
            )
        }
    }
}