package com.example.omdb.ui.search

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.state.SearchScreenState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateSearchToDetails: (ShortContent) -> Unit
) {
    val state by viewModel.state.observeAsState(initial = SearchScreenState())
    val context = LocalContext.current
    SearchView(
        isLoading = viewModel.isLoading,
        state = state,
        onSearchQueryUpdated = viewModel::onSearchQueryUpdated,
        onClearSearchQuery = viewModel::onClearSearchQuery,
        onSearch = viewModel::searchNow,
        onLoadMore = viewModel::loadMore,
        onHold = viewModel::onHold,
        onRelease = viewModel::onRelease,
        onContentClicked = navigateSearchToDetails
    )
    LaunchedEffect(key1 = viewModel.error) {
        val error = viewModel.error?.trim()
        if (error.isNullOrEmpty()) return@LaunchedEffect
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        viewModel.resetError()
    }
}