package com.example.omdb.ui.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.HomeScreenState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateHomeToSearchMovies: () -> Unit,
    navigateHomeToSearchSeries: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = HomeScreenState())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    HomeView(
        snackbarHostState = snackbarHostState,
        state = state,
        onProfileClicked = {
            scope.launch {
                snackbarHostState.showSnackbar(message = "Not there yet!")
            }
        },
        onMovieSelected = navigateHomeToSearchMovies,
        onSeriesSelected = navigateHomeToSearchSeries
    )
}