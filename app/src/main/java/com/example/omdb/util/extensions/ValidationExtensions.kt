package com.example.omdb.util.extensions


fun String.isValidName(): Boolean {
    return trim().isNotEmpty()
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()
}