package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val url: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    @SerializedName("publishedAt") val publishDate: String?,
    val sourceResponse: SourceResponse?,
    val title: String?,
    val urlToImage: String?
)