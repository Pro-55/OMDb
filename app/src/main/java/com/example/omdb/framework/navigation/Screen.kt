package com.example.omdb.framework.navigation

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    data object Router : Screen(
        route = "screen_router",
        arguments = emptyList()
    )

    data object SignUp : Screen(
        route = "screen_sign_up",
        arguments = emptyList()
    )

    data object Home : Screen(
        route = "screen_home",
        arguments = emptyList()
    )

    data object Search : Screen(
        route = "screen_search",
        arguments = emptyList()
    )

    data object Details : Screen(
        route = "screen_details",
        arguments = emptyList()
    )

    data object FullPoster : Screen(
        route = "screen_full_poster",
        arguments = emptyList()
    )

    data object Ratings : Screen(
        route = "screen_ratings",
        arguments = emptyList()
    )

    data object TeamDetails : Screen(
        route = "screen_team_details",
        arguments = emptyList()
    )

    data object Episodes : Screen(
        route = "screen_episodes",
        arguments = emptyList()
    )
}