package com.example.omdb.util.extensions

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

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

fun View.visibleWithFade(parent: ViewGroup, duration: Long = 500) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    visible()
}

fun View.goneWithFade(parent: ViewGroup, duration: Long = 500) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    gone()
}

fun View.invisibleWithFade(parent: ViewGroup, duration: Long = 500) {
    val fadeTransition = Fade().setDuration(duration).addTarget(this)
    TransitionManager.beginDelayedTransition(parent, fadeTransition)
    invisible()
}