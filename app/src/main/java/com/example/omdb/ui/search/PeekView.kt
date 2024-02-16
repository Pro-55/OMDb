package com.example.omdb.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omdb.R
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.util.scaleFadeIn
import com.example.omdb.util.scaleFadeOut
import com.example.omdb.views.ProImage

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
            Surface(
                modifier = Modifier
                    .width(width = 250.dp)
                    .aspectRatio(ratio = 0.75F),
                shape = RoundedCornerShape(size = 8.dp)
            ) {
                ProImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = peekContent?.poster,
                    placeholder = painterResource(id = R.drawable.placeholder_poster),
                    contentDescription = stringResource(id = R.string.cd_poster),
                    contentScale = ContentScale.Crop
                )
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
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
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