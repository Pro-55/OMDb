package com.example.omdb.util.extensions

fun Long.isSuccessful(): Boolean = this > -1

fun List<Long>.isSuccessful(): Boolean = fold(0L) { t, e -> t * e } > -1