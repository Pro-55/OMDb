package com.example.omdb.ui.details.full_poster

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.FullPosterScreenState

@Composable
fun FullPosterScreen(
    viewModel: FullPosterViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = FullPosterScreenState())
    FullPosterView(
        state = state,
        onBack = onBack
    )
}