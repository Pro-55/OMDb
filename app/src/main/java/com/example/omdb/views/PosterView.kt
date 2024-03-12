package com.example.omdb.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.omdb.R

@Composable
fun PosterView(
    modifier: Modifier = Modifier,
    poster: Any?,
    overlayContent: @Composable (() -> Unit)? = null
) {
    ElevatedCard(
        modifier = modifier
            .aspectRatio(ratio = 0.75F),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = poster,
                placeholder = painterResource(id = R.drawable.placeholder_poster),
                contentDescription = stringResource(id = R.string.cd_poster),
                contentScale = ContentScale.Crop
            )
            overlayContent?.invoke()
        }
    }
}