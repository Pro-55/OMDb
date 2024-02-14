package com.example.omdb.ui.details.team_details

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.TeamDetailsScreenState
import com.example.omdb.util.Constants

@Composable
fun TeamDetailsScreen(
    viewModel: TeamDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = TeamDetailsScreenState())
    val context = LocalContext.current
    TeamDetailsView(
        team = state.team,
        onBack = onBack
    )
    LaunchedEffect(key1 = viewModel.error) {
        val error = viewModel.error?.trim()
        if (error.isNullOrEmpty()) return@LaunchedEffect
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        if (Constants.ERROR_MESSAGE_INVALID_REQUEST == error) {
            onBack()
        }
    }
}