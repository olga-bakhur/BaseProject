package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val urlToArticle: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    @SerializedName("publishedAt") val publicationDate: String?,
    @SerializedName("source") val sourceResponse: SourceResponse?,
    val title: String?,
    val urlToImage: String?
)