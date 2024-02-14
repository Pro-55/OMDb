package com.example.omdb.ui.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.state.SearchScreenState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun SearchView(
    isLoading: Boolean,
    state: SearchScreenState,
    onSearchQueryUpdated: (String) -> Unit,
    onClearSearchQuery: () -> Unit,
    onSearch: () -> Unit,
    onLoadMore: () -> Unit,
    onHold: (ShortContent) -> Unit,
    onRelease: () -> Unit,
    onContentClicked: (ShortContent) -> Unit
) {
    var searchBottom by remember { mutableStateOf(Dp(value = 0.0F)) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.result.search.isEmpty()) {
            @DrawableRes val painterRes: Int
            @StringRes val labelRes: Int
            @StringRes val contentDescriptionRes: Int
            when (state.category) {
                Type.MOVIE -> {
                    painterRes = R.drawable.ic_movies
                    labelRes = R.string.label_movies
                    contentDescriptionRes = R.string.cd_movies
                }
                Type.SERIES -> {
                    painterRes = R.drawable.ic_series
                    labelRes = R.string.label_series
                    contentDescriptionRes = R.string.cd_series
                }
                Type.EPISODES -> {
                    painterRes = R.drawable.ic_episodes
                    labelRes = R.string.label_episodes
                    contentDescriptionRes = R.string.cd_episodes
                }
            }
            EmptyContentView(
                modifier = Modifier
                    .fillMaxSize(),
                icon = painterResource(id = painterRes),
                label = stringResource(id = labelRes),
                contentDescription = stringResource(id = contentDescriptionRes)
            )
        } else {
            ContentGrid(
                modifier = Modifier
                    .fillMaxSize(),
                isLoading = isLoading,
                result = state.result,
                searchBottom = searchBottom,
                onLoadMore = onLoadMore,
                onHold = onHold,
                onRelease = onRelease,
                onContentClicked = onContentClicked
            )
        }
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp
                ),
            query = state.query,
            onSearchQueryUpdated = onSearchQueryUpdated,
            onClearSearchQuery = onClearSearchQuery,
            onSearch = onSearch,
            onBottomMeasured = { bottom ->
                searchBottom = bottom
            }
        )
        PeekView(
            modifier = Modifier
                .fillMaxSize(),
            shouldPeek = state.shouldPeek,
            peekContent = state.peekContent
        )
    }
}

@PhoneLightPreview
@Composable
fun SearchViewPreview() {
    OMDbTheme {
        SearchView(
            isLoading = false,
            state = SearchScreenState(
                category = Type.MOVIE,
                query = "",
                result = SearchResult(
                    search = listOf(
                        ShortContent(
                            _id = "tt0372784",
                            title = "Batman Begins",
                            year = "2005",
                            poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt1877830",
                            title = "The Batman",
                            year = "2022",
                            poster = "https://m.media-amazon.com/images/M/MV5BM2MyNTAwZGEtNTAxNC00ODVjLTgzZjUtYmU0YjAzNmQyZDEwXkEyXkFqcGdeQXVyNDc2NTg3NzA@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt2975590",
                            title = "Batman v Superman: Dawn of Justice",
                            year = "2016",
                            poster = "https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt0096895",
                            title = "Batman",
                            year = "1989",
                            poster = "https://m.media-amazon.com/images/M/MV5BZWQ0OTQ3ODctMmE0MS00ODc2LTg0ZTEtZWIwNTUxOGExZTQ4XkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt0103776",
                            title = "Batman Returns",
                            year = "1992",
                            poster = "https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt0118688",
                            title = "Batman & Robin",
                            year = "1997",
                            poster = "https://m.media-amazon.com/images/M/MV5BMGQ5YTM1NmMtYmIxYy00N2VmLWJhZTYtN2EwYTY3MWFhOTczXkEyXkFqcGdeQXVyNTA2NTI0MTY@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt0112462",
                            title = "Batman Forever",
                            year = "1995",
                            poster = "https://m.media-amazon.com/images/M/MV5BNDdjYmFiYWEtYzBhZS00YTZkLWFlODgtY2I5MDE0NzZmMDljXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt4116284",
                            title = "The Lego Batman Movie",
                            year = "2017",
                            poster = " https ://m.media-amazon.com/images/M/MV5BMTcyNTEyOTY0M15BMl5BanBnXkFtZTgwOTAyNzU3MDI@._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt18689424",
                            title = "Batman v Superman: Dawnof Justice (Ultimate Edition)",
                            year = "2016",
                            poster = "https://m.media-amazon.com/images/M/MV5BOTRlNWQwM2ItNjkyZC00MGI3LThkYjktZmE5N2FlMzcyNTIyXkEyXkFqcGdeQXVyMTEyNzgwMDUw._V1_SX300.jpg"
                        ),
                        ShortContent(
                            _id = "tt4853102",
                            title = "Batman: TheKilling Joke",
                            year = "2016",
                            poster = "https://m.media-amazon.com/images/M/MV5BMTdjZTliODYtNWExMi00NjQ1LWIzN2MtN2Q5NTg5NTk3NzliL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"
                        )
                    ),
                    total = 0
                ),
                peekContent = ShortContent(
                    _id = "tt0372784",
                    title = "Batman Begins",
                    year = "2005",
                    poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
                ),
                shouldPeek = true
            ),
            onSearchQueryUpdated = {},
            onClearSearchQuery = {},
            onSearch = {},
            onLoadMore = {},
            onHold = {},
            onRelease = {},
            onContentClicked = {}
        )
    }
}