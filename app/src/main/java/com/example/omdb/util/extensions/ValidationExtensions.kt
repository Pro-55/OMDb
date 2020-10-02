package com.example.omdb.util.extensions

fun String?.isValidEmail(): Boolean {
    val email = this?.trim() ?: return false
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}