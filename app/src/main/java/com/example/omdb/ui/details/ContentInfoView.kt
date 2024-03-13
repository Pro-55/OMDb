package com.example.omdb.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.views.PosterView
import com.example.omdb.views.RatingBar

@Composable
fun ContentInfoView(
    modifier: Modifier = Modifier,
    shortContent: ShortContent?,
    content: Content?,
    onPosterClicked: (String?) -> Unit
) {
    Row(modifier = modifier) {
        PosterView(
            modifier = Modifier
                .width(width = 120.dp)
                .clickable { onPosterClicked(content?.poster ?: shortContent?.poster) },
            poster = content?.poster ?: shortContent?.poster
        )
        if (content != null) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
                    .weight(weight = 1.0F)
            ) {
                Text(
                    text = content.title,
                    maxLines = 3,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = StringBuilder(content.year)
                        .append(" ")
                        .append(stringResource(id = R.string.divider_bullet))
                        .append(" ")
                        .append(content.rated)
                        .append(" ")
                        .append(stringResource(id = R.string.divider_bullet))
                        .append(" ")
                        .append(content.runtime)
                        .toString(),
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = content.genre,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                RatingBar(
                    modifier = Modifier
                        .height(height = 16.dp),
                    max = 10,
                    numStars = 5,
                    rating = content.rating
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = content.language,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}