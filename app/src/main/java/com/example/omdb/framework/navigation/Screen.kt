package com.example.omdb.framework.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.omdb.domain.model.Type

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
        arguments = listOf(
            navArgument(name = "category") {
                type = NavType.EnumType(Type::class.java)
            }
        )
    ) {
        fun getPath(
            category: Type? = null
        ): String = StringBuilder(route)
            .append("?")
            .append("category=")
            .append(category ?: "{category}")
            .toString()
    }

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