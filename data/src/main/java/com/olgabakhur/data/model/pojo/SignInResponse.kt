package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("email") val email: String,
    @SerializedName("accountType") val accountType: Int,
    @SerializedName("sessionId") val sessionId: String
)