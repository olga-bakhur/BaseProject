package com.olgabakhur.data.model.news.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("sessionId") val sessionId: String
)