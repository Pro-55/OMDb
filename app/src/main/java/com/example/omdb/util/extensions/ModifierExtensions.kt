package com.example.omdb.util.extensions

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.example.omdb.domain.model.DragOrientation

@Stable
fun Modifier.detectHoldGestures(
    key1: Any?,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    onHold: () -> Unit,
    onRelease: () -> Unit
): Modifier = this.pointerInput(key1 = key1) {
    detectTapGestures(
        onTap = {
            onClick()
        },
        onPress = {
            val press = PressInteraction.Press(it)
            interactionSource.emit(press)
            tryAwaitRelease()
            interactionSource.emit(PressInteraction.Release(press))
            onRelease()
        },
        onLongPress = {
            onHold()
        }
    )
}

@Stable
fun Modifier.detectDragDismissGestures(
    key1: Any?,
    localDensity: Density,
    localConfiguration: Configuration,
    orientation: DragOrientation,
    onDrag: (xOffset: Dp, yOffset: Dp) -> Unit,
    onDismiss: () -> Unit
): Modifier = this.pointerInput(key1 = key1) {

    fun isHorizontalOutOfBound(
        xOffset: Int,
        threshold: Int
    ): Boolean = xOffset > threshold
            || xOffset < -threshold

    fun isVerticalOutOfBound(
        yOffset: Int,
        threshold: Int
    ): Boolean = yOffset > threshold
            || yOffset < -threshold

    var xOffset = Dp(0.0F)
    var yOffset = Dp(0.0F)

    detectDragGestures(
        onDragEnd = {
            val shouldDismiss = when (orientation) {
                DragOrientation.HORIZONTAL -> isHorizontalOutOfBound(
                    xOffset = xOffset.value.toInt(),
                    threshold = localConfiguration.screenWidthDp / 2
                )
                DragOrientation.VERTICAL -> isVerticalOutOfBound(
                    yOffset = yOffset.value.toInt(),
                    threshold = localConfiguration.screenHeightDp / 2
                )
                DragOrientation.MIXED -> isHorizontalOutOfBound(
                    xOffset = xOffset.value.toInt(),
                    threshold = localConfiguration.screenWidthDp / 2
                )
                        || isVerticalOutOfBound(
                    yOffset = yOffset.value.toInt(),
                    threshold = localConfiguration.screenHeightDp / 2
                )
            }
            xOffset = Dp(0.0F)
            yOffset = Dp(0.0F)
            onDrag(xOffset, yOffset)
            if (shouldDismiss) {
                onDismiss()
            }
        },
        onDragCancel = {
            val shouldDismiss = when (orientation) {
                DragOrientation.HORIZONTAL -> isHorizontalOutOfBound(
                    xOffset = xOffset.value.toInt(),
                    threshold = localConfiguration.screenWidthDp / 2
                )
                DragOrientation.VERTICAL -> isVerticalOutOfBound(
                    yOffset = yOffset.value.toInt(),
                    threshold = localConfiguration.screenHeightDp / 2
                )
                DragOrientation.MIXED -> isHorizontalOutOfBound(
                    xOffset = xOffset.value.toInt(),
                    threshold = localConfiguration.screenWidthDp / 2
                )
                        || isVerticalOutOfBound(
                    yOffset = yOffset.value.toInt(),
                    threshold = localConfiguration.screenHeightDp / 2
                )
            }
            xOffset = Dp(0.0F)
            yOffset = Dp(0.0F)
            onDrag(xOffset, yOffset)
            if (shouldDismiss) {
                onDismiss()
            }
        },
        onDrag = { change, dragAmount ->
            change.consume()
            val newDragAmount = when (orientation) {
                DragOrientation.HORIZONTAL -> dragAmount.copy(y = 0.0F)
                DragOrientation.VERTICAL -> dragAmount.copy(x = 0.0F)
                DragOrientation.MIXED -> dragAmount
            }
            with(localDensity) {
                xOffset += newDragAmount.x.toDp()
                yOffset += newDragAmount.y.toDp()
                onDrag(xOffset, yOffset)
            }
        }
    )
}