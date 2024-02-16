package com.example.omdb.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.omdb.R

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.surface,
    onBackground: Color = MaterialTheme.colorScheme.onSurface,
    rating: Float,
    max: Int,
    numStars: Int
) {
    Surface(
        modifier = modifier,
        color = background
    ) {
        val percent = remember { (rating / max) * numStars }
        LazyRow {
            items(count = numStars) {
                val current = remember { it + 1 }
                val activeWeight = remember {
                    when {
                        current <= percent -> 1.0F
                        current.toFloat() - percent < 1.0F -> percent - percent.toInt()
                        else -> 0.0F
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(end = if (current < numStars) 2.dp else 0.dp)
                        .fillMaxHeight()
                        .aspectRatio(ratio = 1.0F)
                        .clip(shape = RoundedCornerShape(size = 2.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (activeWeight != 0.0F) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(weight = activeWeight)
                                    .background(color = onBackground)
                            )
                        }
                        if (activeWeight < 1.0F) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(weight = 1.0F - activeWeight)
                                    .background(color = background)
                            )
                        }
                    }
                    Icon(
                        modifier = Modifier
                            .padding(all = 2.dp),
                        painter = painterResource(id = R.drawable.ic_notification_badge),
                        contentDescription = null,
                        tint = background
                    )
                }
            }
        }
    }
}