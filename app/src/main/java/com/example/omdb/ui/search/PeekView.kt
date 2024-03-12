package com.example.omdb.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.util.anim.scaleFadeIn
import com.example.omdb.util.anim.scaleFadeOut
import com.example.omdb.views.PosterView

@Composable
fun PeekView(
    modifier: Modifier = Modifier,
    shouldPeek: Boolean,
    peekContent: ShortContent?
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = shouldPeek,
            enter = fadeIn(animationSpec = tween()),
            exit = fadeOut(animationSpec = tween())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5F))
            )
        }
        AnimatedVisibility(
            visible = shouldPeek,
            enter = scaleFadeIn(initialScale = 0.8F),
            exit = scaleFadeOut(targetScale = 0.8F)
        ) {
            PosterView(
                modifier = Modifier
                    .width(width = 250.dp),
                poster = peekContent?.poster
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.scrim.copy(alpha = 0.5F)
                                )
                            )
                        )
                        .padding(all = 16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = StringBuilder(peekContent?.title ?: "")
                            .append(" ")
                            .append(stringResource(id = R.string.divider_bullet))
                            .append(" (")
                            .append(peekContent?.year)
                            .append(")")
                            .toString(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black,
                                blurRadius = 2.0F
                            )
                        )
                    )
                }
            }
        }
    }
}