package com.example.omdb.util.anim

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin

@Stable
fun scaleFadeIn(
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
    initialScale: Float = 0F,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    initialAlpha: Float = 0F
): EnterTransition = scaleIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    ),
    initialScale = initialScale,
    transformOrigin = transformOrigin
) + fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    ),
    initialAlpha = initialAlpha
)