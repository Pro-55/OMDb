package com.example.omdb.util.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

fun FragmentActivity.glide() = Glide.with(this)

fun Fragment.glide() = Glide.with(this)
