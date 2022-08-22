package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.useCases.NewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val newsUseCase: NewsUseCase
) {

    // News Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<List<Article>> =
        newsUseCase.getBreakingNews(countryCode, pageNumber)

    // News database
    suspend fun insertArticle(article: Article): Result<Long> =
        newsUseCase.insertArticle(article)

    suspend fun getSavedArticles(): Result<Flow<List<Article>>> =
        newsUseCase.getSavedArticles()

    suspend fun deleteArticle(article: Article): Result<Int> =
        newsUseCase.deleteArticle(article)
}