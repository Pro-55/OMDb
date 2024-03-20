package com.example.omdb.ui.details.team_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.TeamDetailsScreenState

@Composable
fun TeamDetailsScreen(
    viewModel: TeamDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = TeamDetailsScreenState())
    TeamDetailsView(
        team = state.team,
        onBack = onBack
    )
}