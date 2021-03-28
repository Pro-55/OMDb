package com.example.omdb.util.extensions

import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import com.example.omdb.util.transition.Scale

fun View.visible() {
    if (!isVisible()) visibility = View.VISIBLE
}

fun View.invisible() {
    if (!isInvisible()) visibility = View.INVISIBLE
}

fun View.gone() {
    if (!isGone()) visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.visibleWithFade(parent: ViewGroup, duration: Long = 300) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    visible()
}

fun View.goneWithFade(parent: ViewGroup, duration: Long = 300) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    gone()
}

fun View.invisibleWithFade(parent: ViewGroup, duration: Long = 300) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    invisible()
}

fun View.visibleWithScaleFade(
    parent: ViewGroup,
    duration: Long = 300,
    minScale: Float = 0.8F,
    direction: Scale.Direction? = null,
    pivotX: Float = 0F,
    pivotY: Float = 0F
) {
    val transitionSet = TransitionSet().apply {
        addTransition(
            Scale(
                minScale = minScale,
                direction = direction,
                pivotX = pivotX,
                pivotY = pivotY
            )
        )
        addTransition(Fade())
        this.duration = duration
        addTarget(this@visibleWithScaleFade)
    }
    TransitionManager.beginDelayedTransition(parent, transitionSet)
    visible()
}

fun View.goneWithScaleFade(
    parent: ViewGroup,
    duration: Long = 300,
    minScale: Float = 0.8F,
    direction: Scale.Direction? = null,
    pivotX: Float = 0F,
    pivotY: Float = 0F
) {
    val transitionSet = TransitionSet().apply {
        addTransition(
            Scale(
                minScale = minScale,
                direction = direction,
                pivotX = pivotX,
                pivotY = pivotY
            )
        )
        addTransition(Fade())
        this.duration = duration
        addTarget(this@goneWithScaleFade)
    }
    TransitionManager.beginDelayedTransition(parent, transitionSet)
    gone()
}

fun View.invisibleWithScaleFade(
    parent: ViewGroup,
    duration: Long = 300,
    minScale: Float = 0.8F,
    direction: Scale.Direction? = null,
    pivotX: Float = 0F,
    pivotY: Float = 0F
) {
    val transitionSet = TransitionSet().apply {
        addTransition(
            Scale(
                minScale = minScale,
                direction = direction,
                pivotX = pivotX,
                pivotY = pivotY
            )
        )
        addTransition(Fade())
        this.duration = duration
        addTarget(this@invisibleWithScaleFade)
    }
    TransitionManager.beginDelayedTransition(parent, transitionSet)
    invisible()
}

fun View.animateTranslate(tX: Float = 0F, tY: Float = 0F, duration: Long = 0) {
    animate().x(tX).y(tY).setDuration(duration).start()
}

fun View.animateScale(sX: Float = 1F, sY: Float = 1F, duration: Long = 0) {
    animate().scaleX(sX).scaleY(sY).setDuration(duration).start()
}