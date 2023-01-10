package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.usecases.ArticleUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleInteractor @Inject constructor(
    private val articleUseCase: ArticleUseCase
) {

    // Api
    suspend fun getArticlesList(countryCode: String, pageNumber: Int): Result<List<Article>> =
        articleUseCase.getArticlesList(countryCode, pageNumber)

    // Database
    suspend fun getSavedArticlesList(): Flow<List<Article>> = articleUseCase.getSavedArticlesList()

    suspend fun saveArticle(article: Article): Result<Unit> =
        articleUseCase.saveArticle(article)

    suspend fun deleteArticle(article: Article): Result<Unit> =
        articleUseCase.deleteArticle(article)
}