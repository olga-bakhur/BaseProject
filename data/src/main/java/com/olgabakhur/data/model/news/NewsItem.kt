package com.olgabakhur.data.model.news

data class NewsItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)