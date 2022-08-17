package com.olgabakhur.data.repository

import com.olgabakhur.data.model.news.auth.AuthRequest
import com.olgabakhur.data.model.news.auth.AuthResponse
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // News Api
    suspend fun signIn(authRequest: AuthRequest): Result<AuthResponse>
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem>

    // News database
    suspend fun insertArticle(article: Article): Result<Long>
    suspend fun getSavedArticles(): Result<Flow<List<Article>>>
    suspend fun deleteArticle(article: Article): Result<Int>
}