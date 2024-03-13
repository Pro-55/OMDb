package com.example.omdb.framework.navigation

import android.content.Intent
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

object DeepLinks {
    private const val DEEP_LINK_HOST = "https://omdb.example.com"

    val list: List<NavDeepLink> = listOf(
        navDeepLink {
            action = Intent.ACTION_VIEW
            uriPattern = getDetailsDeepLink()
        }
    )

    fun getDetailsDeepLink(
        contentId: String? = null
    ): String = StringBuilder(DEEP_LINK_HOST)
        .append("/")
        .append("details")
        .append("?")
        .append("contentId=")
        .append(contentId ?: "{contentId}")
        .toString()
}