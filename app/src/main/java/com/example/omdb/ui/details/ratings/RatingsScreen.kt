package com.example.omdb.ui.details.ratings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RatingsScreen(
    viewModel: RatingsViewModel = hiltViewModel()
) {
    RatingsView()
}