package com.olgabakhur.data.repository

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // News Api
    suspend fun signIn(email: String, password: String): Result<UserCredentials>
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<List<Article>>

    // News database
    suspend fun insertArticle(article: Article): Result<Long>
    suspend fun getSavedArticles(): Result<Flow<List<Article>>>
    suspend fun deleteArticle(article: Article): Result<Int>
}