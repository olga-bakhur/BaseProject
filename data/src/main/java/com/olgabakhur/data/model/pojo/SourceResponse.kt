package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("id") val id: Any?,
    @SerializedName("name") val name: String?
)