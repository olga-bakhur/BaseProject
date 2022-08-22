package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("url") val url: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("source") val sourceResponse: SourceResponse?,
    @SerializedName("title") val title: String?,
    @SerializedName("urlToImage") val urlToImage: String?
)