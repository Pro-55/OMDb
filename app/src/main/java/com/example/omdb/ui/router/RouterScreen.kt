package com.example.omdb.ui.router

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RouterScreen(
    viewModel: RouterViewModel = hiltViewModel(),
    navigateRouterToSignUp: () -> Unit,
    navigateRouterToHome: () -> Unit,
    deepLinkToDetails: (String) -> Unit
) {
    LaunchedEffect(key1 = viewModel.loginStatus) {
        when (viewModel.loginStatus) {
            false -> navigateRouterToSignUp()
            true -> {
                val contentId = viewModel.contentId
                if (!contentId.isNullOrEmpty()) {
                    deepLinkToDetails(contentId)
                } else {
                    navigateRouterToHome()
                }
            }
            else -> Unit
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel.error) {
        val error = viewModel.error?.trim()
        if (error.isNullOrEmpty()) return@LaunchedEffect
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        (context as? Activity)?.finish()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
}