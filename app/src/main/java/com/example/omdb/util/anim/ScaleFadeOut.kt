package com.example.omdb.util.anim

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin

@Stable
fun scaleFadeOut(
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
    targetScale: Float = 0F,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    targetAlpha: Float = 0F
): ExitTransition = scaleOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    ),
    targetScale = targetScale,
    transformOrigin = transformOrigin
) + fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    ),
    targetAlpha = targetAlpha
)