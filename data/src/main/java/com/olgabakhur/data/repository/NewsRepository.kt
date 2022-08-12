package com.olgabakhur.data.repository

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import kotlinx.coroutines.flow.Flow
import com.olgabakhur.data.util.result.Result

interface NewsRepository {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem>
    suspend fun upsert(article: Article): Long
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}