package com.example.omdb.ui.details

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.model.Rating
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.domain.state.DetailsScreenState
import com.example.omdb.util.Constants

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    navigateDetailsToFullPoster: (String?) -> Unit,
    navigateDetailsToRatings: (List<Rating>) -> Unit,
    navigateDetailsToTeamDetails: (TeamDetails) -> Unit,
    navigateDetailsToEpisodes: (String, Int) -> Unit

) {
    val state by viewModel.state.observeAsState(initial = DetailsScreenState())
    val context = LocalContext.current
    DetailsView(
        state = state,
        onBack = onBack,
        onShare = {},
        onPosterClicked = navigateDetailsToFullPoster,
        onRatingsClicked = navigateDetailsToRatings,
        onTeamClicked = navigateDetailsToTeamDetails,
        onSeasonSelected = navigateDetailsToEpisodes
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