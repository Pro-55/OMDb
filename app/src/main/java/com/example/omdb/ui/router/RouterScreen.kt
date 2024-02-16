package com.example.omdb.ui.router

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RouterScreen(
    viewModel: RouterViewModel = hiltViewModel(),
    navigateRouterToSignUp: () -> Unit,
    navigateRouterToHome: () -> Unit
) {
    LaunchedEffect(key1 = viewModel.loginStatus) {
        when (viewModel.loginStatus) {
            false -> navigateRouterToSignUp()
            true -> navigateRouterToHome()
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
}