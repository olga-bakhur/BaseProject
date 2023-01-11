package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("name") val sourceName: String?
)