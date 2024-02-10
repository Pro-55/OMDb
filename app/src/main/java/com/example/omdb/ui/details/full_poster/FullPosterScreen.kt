package com.example.omdb.ui.details.full_poster

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FullPosterScreen(
    viewModel: FullPosterViewModel = hiltViewModel()
) {
    FullPosterView()
}