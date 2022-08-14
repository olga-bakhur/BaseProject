package com.olgabakhur.domain.repository

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.domain.util.result.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // News Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem>

    // News database
    suspend fun upsert(article: Article): Long
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}