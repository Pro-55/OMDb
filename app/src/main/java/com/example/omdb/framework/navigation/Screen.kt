package com.example.omdb.framework.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.omdb.domain.model.Rating
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.model.Type
import com.google.gson.Gson
import com.example.omdb.domain.model.TeamDetails as Team

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
        arguments = listOf(
            navArgument(name = "shortContent") {
                type = NavType.StringType
                nullable = true
            },
            navArgument(name = "contentId") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        fun getPath(
            shortContent: ShortContent? = null,
            contentId: String? = null
        ): String = StringBuilder(route)
            .append("?")
            .apply {
                when {
                    shortContent == null && contentId == null -> append("shortContent={shortContent}")
                        .append("&")
                        .append("contentId={contentId}")
                    shortContent != null -> {
                        val content = Uri.encode(Gson().toJson(shortContent))
                        append("shortContent=$content")
                            .append("&")
                            .append("contentId=null")
                    }
                    else -> append("contentId=$contentId")
                        .append("&")
                        .append("shortContent=null")
                }
            }
            .toString()
    }

    data object FullPoster : Screen(
        route = "screen_full_screen",
        arguments = listOf(
            navArgument(name = "posterUrl") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        fun getPath(
            posterUrl: String? = null
        ): String = StringBuilder(route)
            .append("?")
            .append("posterUrl=")
            .apply {
                if (posterUrl == null) {
                    append("{posterUrl}")
                } else {
                    append(posterUrl)
                }
            }
            .toString()
    }

    data object Ratings : Screen(
        route = "screen_ratings",
        arguments = listOf(
            navArgument(name = "ratings") {
                type = NavType.StringType
            }
        )
    ) {
        fun getPath(
            ratings: List<Rating>? = null
        ): String = StringBuilder(route)
            .append("?")
            .append("ratings=")
            .apply {
                if (ratings == null) {
                    append("{ratings}")
                } else {
                    append(Uri.encode(Gson().toJson(ratings)))
                }
            }
            .toString()
    }

    data object TeamDetails : Screen(
        route = "screen_team_details",
        arguments = listOf(
            navArgument(name = "team") {
                type = NavType.StringType
            }
        )
    ) {
        fun getPath(
            team: Team? = null
        ): String = StringBuilder(route)
            .append("?")
            .append("team=")
            .apply {
                if (team == null) {
                    append("{team}")
                } else {
                    append(Uri.encode(Gson().toJson(team)))
                }
            }
            .toString()
    }

    data object Episodes : Screen(
        route = "screen_episodes",
        arguments = listOf(
            navArgument(name = "contentId") {
                type = NavType.StringType
            },
            navArgument(name = "season") {
                type = NavType.IntType
            }
        )
    ) {
        fun getPath(
            contentId: String? = null,
            season: Int? = null
        ): String = StringBuilder(route)
            .append("?")
            .apply {
                if (contentId == null && season == null) {
                    append("contentId={contentId}")
                        .append("&")
                        .append("season={season}")
                } else {
                    append("contentId=$contentId")
                        .append("&")
                        .append("season=$season")
                }
            }
            .toString()
    }
}