package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val title: String?,
    val content: String?,
    @SerializedName("source") val sourceResponse: SourceResponse?,
    @SerializedName("publishedAt") val publicationDate: String?,
    @SerializedName("url") val urlToArticle: String?,
    val urlToImage: String?
)