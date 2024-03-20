package com.example.omdb.ui.details.ratings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.RatingsScreenState

@Composable
fun RatingsScreen(
    viewModel: RatingsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = RatingsScreenState())
    RatingsView(
        ratings = state.ratings,
        onBack = onBack
    )
}