package com.example.omdb.ui.details.episodes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel()
) {
    EpisodesView()
}