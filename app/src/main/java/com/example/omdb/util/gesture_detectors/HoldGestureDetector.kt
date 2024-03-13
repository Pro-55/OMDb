package com.example.omdb.util.gesture_detectors

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Stable
fun Modifier.detectHoldGesture(
    key1: Any?,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    onHold: () -> Unit,
    onRelease: () -> Unit,
    onTapStateChange: (Boolean) -> Unit
): Modifier = this.pointerInput(key1 = key1) {
    detectTapGestures(
        onTap = {
            onTapStateChange(false)
            onClick()
        },
        onPress = {
            onTapStateChange(true)
            val press = PressInteraction.Press(it)
            interactionSource.emit(press)
            tryAwaitRelease()
            interactionSource.emit(PressInteraction.Release(press))
            onRelease()
        },
        onLongPress = {
            onTapStateChange(false)
            onHold()
        }
    )
}