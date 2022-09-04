package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    val email: String,
    val accountType: Int,
    @SerializedName("sid") val sessionId: String
)