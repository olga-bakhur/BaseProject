package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles") val listArticlesResponse: List<ArticleResponse>,
    val status: String,
    val totalResults: Int
)