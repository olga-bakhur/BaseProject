package com.olgabakhur.data.repository

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertArticle(article: Article): Result<Long>
    suspend fun getSavedArticles(): Result<Flow<List<Article>>>
    suspend fun deleteArticle(article: Article): Result<Int>
}