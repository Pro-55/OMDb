package com.example.omdb.util.extensions

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.R

fun FragmentActivity.glide() = Glide.with(this)

fun Fragment.glide() = Glide.with(this)

fun <T> RequestBuilder<T>.diskCacheStrategyAll(): RequestBuilder<T> =
    diskCacheStrategy(DiskCacheStrategy.ALL)

fun <T> RequestBuilder<T>.addProfilePlaceholder(context: Context): RequestBuilder<T> = placeholder(
    AppCompatResources.getDrawable(context, R.drawable.ic_profile_placeholder)
)

fun <T> RequestBuilder<T>.addPosterPlaceholder(context: Context): RequestBuilder<T> = placeholder(
    AppCompatResources.getDrawable(context, R.drawable.placeholder_poster)
)