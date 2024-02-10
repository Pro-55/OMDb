package com.example.omdb.ui.details.team_details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TeamDetailsScreen(
    viewModel: TeamDetailsViewModel = hiltViewModel()
) {
    TeamDetailsView()
}