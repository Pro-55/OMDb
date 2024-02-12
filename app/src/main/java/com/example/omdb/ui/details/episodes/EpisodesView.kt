package com.example.omdb.ui.details.episodes

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omdb.R
import com.example.omdb.domain.model.Episode
import com.example.omdb.domain.state.EpisodesScreenState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ActionBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodesView(
    snackbarHostState: SnackbarHostState,
    state: EpisodesScreenState,
    onBack: () -> Unit,
    onEpisodeSelected: (Episode) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ActionBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.label_episodes),
                onBack = onBack
            )
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(items = state.episodes) { episode ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEpisodeSelected(episode) }
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(
                            text = episode.title,
                            maxLines = 1,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .height(height = 8.dp)
                        )
                        Text(
                            text = StringBuilder("S")
                                .append(state.season)
                                .append(" ")
                                .append(stringResource(id = R.string.divider_bullet))
                                .append(" ")
                                .append("E")
                                .append(episode.episode)
                                .append(" | ")
                                .append(episode.released)
                                .toString(),
                            maxLines = 1,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }
            }
        }
    }
}

@PhoneLightPreview
@Composable
fun EpisodesViewPreview() {
    OMDbTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        EpisodesView(
            snackbarHostState = snackbarHostState,
            state = EpisodesScreenState(
                _id = "tt0108778",
                season = 1,
                episodes = listOf(
                    Episode(
                        _id = "tt0583459",
                        title = "The One Where Monica Gets a Roommate",
                        episode = "1",
                        released = "1994 - 09 - 22"
                    ),
                    Episode(
                        _id = "tt0583647",
                        title = "The One with the Sonogram at the End",
                        episode = "2",
                        released = "1994 - 09 - 29"
                    ),
                    Episode(
                        _id = "tt0583653",
                        title = "The One with the Thumb",
                        episode = "3",
                        released = "1994 - 10 - 06"
                    ),
                    Episode(
                        _id = "tt0583521",
                        title = "The One with George Stephanopoulos",
                        episode = "4",
                        released = "1994 - 10 - 13"
                    ),
                    Episode(
                        _id = "tt0583599",
                        title = "The One with the East German Laundry Detergent",
                        episode = "5",
                        released = "1994 - 10 - 20"
                    ),
                    Episode(
                        _id = "tt0583585",
                        title = "The One with the Butt",
                        episode = "6",
                        released = "1994 - 10 - 27"
                    ),
                    Episode(
                        _id = "tt0583579",
                        title = "The One with the Blackout",
                        episode = "7",
                        released = "1994 - 11 - 03"
                    ),
                    Episode(
                        _id = "tt0583462",
                        title = "The One Where Nana Dies Twice",
                        episode = "8",
                        released = "1994 - 11 - 10"
                    ),
                    Episode(
                        _id = "tt0583492",
                        title = "The One Where Underdog Gets Away",
                        episode = "9",
                        released = "1994 - 11 - 17"
                    ),
                    Episode(
                        _id = "tt0583630",
                        title = "The One with the Monkey",
                        episode = "10",
                        released = "1994 - 12 - 15"
                    ),
                    Episode(
                        _id = "tt0583534",
                        title = "The One with Mrs . Bing",
                        episode = "11",
                        released = "1995 - 01 - 05"
                    ),
                    Episode(
                        _id = "tt0583598",
                        title = "The One with the Dozen Lasagnas",
                        episode = "12",
                        released = "1995 - 01 - 12"
                    ),
                    Episode(
                        _id = "tt0583582",
                        title = "The One with the Boobies",
                        episode = "13",
                        released = "1995 - 01 - 19"
                    ),
                    Episode(
                        _id = "tt0583587",
                        title = "The One with the Candy Hearts",
                        episode = "14",
                        released = "1995 - 02 - 09"
                    ),
                    Episode(
                        _id = "tt0583649",
                        title = "The One with the Stoned Guy",
                        episode = "15",
                        released = "1995 - 02 - 16"
                    ),
                    Episode(
                        _id = "tt0583568",
                        title = "The One with Two Parts: Part 1",
                        episode = "16",
                        released = "1995 - 02 - 23"
                    ),
                    Episode(
                        _id = "tt0583569",
                        title = "The One with Two Parts: Part 2",
                        episode = "17",
                        released = "1995 - 02 - 23"
                    ),
                    Episode(
                        _id = "tt0583510",
                        title = "The One with All the Poker",
                        episode = "18",
                        released = "1995 - 03 - 02"
                    ),
                    Episode(
                        _id = "tt0583493",
                        title = "The One Where the Monkey Gets Away",
                        episode = "19",
                        released = "1995 - 03 - 09"
                    ),
                    Episode(
                        _id = "tt0583602",
                        title = "The One with the Evil Orthodontist",
                        episode = "20",
                        released = "1995 - 04 - 06"
                    ),
                    Episode(
                        _id = "tt0583603",
                        title = "The One with the Fake Monica",
                        episode = "21",
                        released = "1995 - 04 - 27"
                    ),
                    Episode(
                        _id = "tt0583616",
                        title = "The One with the Ick Factor",
                        episode = "22",
                        released = "1995 - 05 - 04"
                    ),
                    Episode(
                        _id = "tt0583577",
                        title = "The One with the Birth",
                        episode = "23",
                        released = "1995 - 05 - 11"
                    ),
                    Episode(
                        _id = "tt0583469",
                        title = "The One Where Rachel Finds Out",
                        episode = "24",
                        released = "1995 - 05 - 18"
                    )
                )
            ),
            onBack = {},
            onEpisodeSelected = {}
        )
    }
}