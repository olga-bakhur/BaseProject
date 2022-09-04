package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    val id: Any?,
    @SerializedName("name") val sourceName: String?
)