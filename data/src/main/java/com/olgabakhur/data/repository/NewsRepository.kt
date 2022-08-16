package com.olgabakhur.data.repository

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // News Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem>

    // News database
    suspend fun insertArticle(article: Article): Result<Long>
    suspend fun getSavedArticles(): Result<Flow<List<Article>>>
    suspend fun deleteArticle(article: Article): Result<Int>
}