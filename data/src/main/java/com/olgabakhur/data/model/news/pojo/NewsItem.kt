package com.olgabakhur.data.model.news.pojo

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int
)