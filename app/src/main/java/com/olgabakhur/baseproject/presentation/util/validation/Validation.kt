package com.olgabakhur.baseproject.presentation.util.validation

/* Email */
fun isEmailValid(email: String): Boolean {
    val pattern = Regex(EMAIL)
    return pattern.matches(email)
}

/* Password */
fun isPasswordValid(password: String): Boolean {
    val pattern = Regex(PASSWORD)
    return pattern.matches(password)
}