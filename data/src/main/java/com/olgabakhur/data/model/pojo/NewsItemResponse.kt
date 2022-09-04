package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class NewsItemResponse(
    @SerializedName("articles") val articlesList: List<ArticleResponse>,
    val status: String,
    val totalResults: Int
)