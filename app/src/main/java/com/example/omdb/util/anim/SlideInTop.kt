package com.example.omdb.util.anim

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.IntOffset

@Stable
fun slideInTop(
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing
): EnterTransition = slideIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    ),
    initialOffset = {
        IntOffset(
            x = 0,
            y = -it.height
        )
    }
)