package com.example.omdb.ui.details.ratings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.omdb.domain.model.Rating
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ActionBar

@Composable
fun RatingsView(
    ratings: List<Rating>?,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(id = R.string.label_ratings),
            onBack = onBack
        )
        if (ratings.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.label_no_ratings),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                items(items = ratings) { rating ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = rating.value,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .height(height = 4.dp)
                        )
                        Text(
                            text = rating.source,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
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
fun RatingsViewPreview() {
    OMDbTheme {
        RatingsView(
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
            onBack = {}
        )
    }
}