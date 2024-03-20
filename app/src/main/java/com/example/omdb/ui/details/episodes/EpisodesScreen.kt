package com.example.omdb.ui.details.episodes

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.model.Episode
import com.example.omdb.domain.state.EpisodesScreenState

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
    onBack: () -> Unit,
    navigateEpisodesToDetails: (Episode) -> Unit
) {
    val state by viewModel.state.observeAsState(initial = EpisodesScreenState())
    val snackbarHostState = remember { SnackbarHostState() }
    EpisodesView(
        snackbarHostState = snackbarHostState,
        state = state,
        onBack = onBack,
        onEpisodeSelected = navigateEpisodesToDetails
    )
    LaunchedEffect(key1 = viewModel.error) {
        val error = viewModel.error?.trim()
        if (error.isNullOrEmpty()) return@LaunchedEffect
        snackbarHostState.showSnackbar(message = error)
    }
}