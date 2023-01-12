package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.usecases.GetArticleUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleInteractor @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) {

    // Api
    suspend fun getArticlesList(countryCode: String, pageNumber: Int): Result<List<Article>> =
        getArticleUseCase.getArticlesList(countryCode, pageNumber)

    // Database
    suspend fun getSavedArticlesList(): Flow<List<Article>> =
        getArticleUseCase.getSavedArticlesList()

    suspend fun saveArticle(article: Article): Result<Unit> =
        getArticleUseCase.saveArticle(article)

    suspend fun deleteArticle(article: Article): Result<Unit> =
        getArticleUseCase.deleteArticle(article)
}