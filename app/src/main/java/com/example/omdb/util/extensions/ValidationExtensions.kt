package com.example.omdb.util.extensions

import androidx.core.util.PatternsCompat.EMAIL_ADDRESS

fun String?.isValidEmail(): Boolean {
    val email = this?.trim() ?: return false
    return EMAIL_ADDRESS.matcher(email).matches()
}