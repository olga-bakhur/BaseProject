package com.olgabakhur.baseproject.presentation.util.validation

// Email
private fun isEmailValid(email: String): Boolean {
    val pattern = Regex(EMAIL)
    return pattern.matches(email)
}

// Password
fun isPasswordValid(password: String): Boolean {
    val pattern = Regex(PASSWORD)
    return pattern.matches(password)
}

fun isPasswordMatches(password: String, repeat: String): Boolean =
    password.contentEquals(repeat, false)