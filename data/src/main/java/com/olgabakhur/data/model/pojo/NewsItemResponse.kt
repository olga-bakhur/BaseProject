package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class NewsItemResponse(
    @SerializedName("articles") val articleResponses: List<ArticleResponse>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)