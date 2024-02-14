package com.example.omdb.util.extensions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

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