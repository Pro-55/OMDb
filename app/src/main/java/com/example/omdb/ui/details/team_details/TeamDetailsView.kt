package com.example.omdb.ui.details.team_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.Constants
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ActionBar

@Composable
fun TeamDetailsView(
    team: TeamDetails?,
    onBack: () -> Unit
) {
    val scroll = rememberScrollState(initial = 0)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(id = R.string.label_team),
            onBack = onBack
        )
        if (team == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.label_no_team_details),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scroll)
            ) {
                TeamInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        ),
                    label = stringResource(id = R.string.label_cast),
                    info = team.cast
                )
                TeamInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        ),
                    label = stringResource(id = R.string.label_crew),
                    info = team.crew
                )
                TeamInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        ),
                    label = stringResource(id = R.string.label_director),
                    info = team.director
                )
                TeamInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    label = stringResource(id = R.string.label_production),
                    info = team.production ?: Constants.NOT_AVAILABLE
                )
            }
        }
    }
}

@PhoneLightPreview
@Composable
fun TeamDetailsViewPreview() {
    OMDbTheme {
        TeamDetailsView(
            team = TeamDetails(
                cast = "Christian Bale, Michael Caine, Ken Watanabe",
                crew = "Bob Kane (characters), David S. Goyer (story), Christopher Nolan (screenplay), David S. Goyer (screenplay)",
                director = "Christopher Nolan",
                production = "N/A"
            ),
            onBack = {}
        )
    }
}