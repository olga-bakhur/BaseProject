package com.olgabakhur.data.model.dto

import java.io.Serializable

data class UserCredentials(
    val email: String,
    val accountType: Int,
    val sessionId: String
): Serializable