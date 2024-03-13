package com.example.omdb.ui.details

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Rating
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.state.DetailsScreenState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ActionBar

@Composable
fun DetailsView(
    state: DetailsScreenState,
    onBack: () -> Unit,
    onShare: (Context, Content) -> Unit,
    onPosterClicked: (String?) -> Unit,
    onRatingsClicked: (List<Rating>) -> Unit,
    onTeamClicked: (TeamDetails) -> Unit,
    onSeasonSelected: (String, Int) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(id = R.string.label_details),
            onBack = onBack,
            actionContent = {
                state.content?.let { safeContent ->
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(
                                ratio = 1.0F,
                                matchHeightConstraintsFirst = true
                            )
                            .clickable { onShare(context, safeContent) }
                            .padding(all = 16.dp),
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(id = R.string.cd_share_button)
                    )
                }
            }
        )
        ContentInfoView(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            shortContent = state.shortContent,
            content = state.content,
            onPosterClicked = onPosterClicked
        )
        state.content?.let { safeContent ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.label_plot),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                text = safeContent.plot,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            MoreInfoView(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(id = R.string.label_ratings),
                onClick = {
                    onRatingsClicked(safeContent.ratings)
                }
            )
            MoreInfoView(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(id = R.string.label_team),
                onClick = {
                    onTeamClicked(safeContent.team)
                }
            )
            if (safeContent.seasons > 0) {
                Spacer(
                    modifier = Modifier
                        .height(height = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.label_seasons),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(height = 8.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(count = safeContent.seasons) { season ->
                        SuggestionChip(
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentWidth()
                                .padding(all = 0.dp),
                            shape = CircleShape,
                            label = {
                                Text(
                                    text = "Season ${season + 1}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = {
                                onSeasonSelected(safeContent._id, season + 1)
                            }
                        )
                    }
                }
            }
        }
    }
}

@PhoneLightPreview
@Composable
fun DetailsViewPreview() {
    OMDbTheme {
        DetailsView(
            state = DetailsScreenState(
                shortContent = ShortContent(
                    _id = "tt0372784",
                    title = "Batman Begins",
                    year = "2005",
                    poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
                ),
                content = Content(
                    _id = "tt0372784",
                    type = Type.MOVIE,
                    poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
                    title = "Batman Begins",
                    year = "2005",
                    rated = "PG-13",
                    runtime = "140 min",
                    genre = "Action, Crime, Drama",
                    team = TeamDetails(
                        cast = "Christian Bale, Michael Caine, Ken Watanabe",
                        crew = "Bob Kane, David S. Goyer, Christopher Nolan",
                        director = "Christopher Nolan",
                        production = "N/A"
                    ),
                    plot = "After witnessing his parents ' death, Bruce learns the art of fighting to confront injustice. When he returns to Gotham as Batman, he must stop a secret society that intends to destroy the city.",
                    language = "English, Mandarin",
                    ratings = listOf(
                        Rating(
                            source = "Internet Movie Database",
                            value = "8.2/10"
                        ),
                        Rating(
                            source = "Rotten Tomatoes",
                            value = "85%"
                        ),
                        Rating(
                            source = "Metacritic",
                            value = "70/100"
                        )
                    ),
                    rating = 4.1F,
                    seasons = 10,
                    isFavorite = false
                )
            ),
            onBack = {},
            onShare = { _, _ -> },
            onPosterClicked = {},
            onRatingsClicked = {},
            onTeamClicked = {},
            onSeasonSelected = { _, _ -> }
        )
    }
}