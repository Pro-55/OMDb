package com.example.omdb.util.extensions

import android.view.View

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