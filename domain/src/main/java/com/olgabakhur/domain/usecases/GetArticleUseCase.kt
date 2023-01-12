package com.olgabakhur.domain.usecases

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.repository.ArticleRepository
import com.olgabakhur.data.repository.DatabaseRepository
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val databaseRepository: DatabaseRepository
) {

    // Api
    suspend fun getArticlesList(countryCode: String, pageNumber: Int): Result<List<Article>> =
        articleRepository.getArticlesList(countryCode, pageNumber)

    // Database
    suspend fun getSavedArticlesList(): Flow<List<Article>> =
        when (val result = databaseRepository.getSavedArticlesList()) {
            is Result.Success -> {
                result.value
            }

            is Result.Error -> {
                emptyFlow()
            }
        }

    suspend fun saveArticle(article: Article): Result<Unit> =
        databaseRepository.saveArticle(article)

    suspend fun deleteArticle(article: Article): Result<Unit> =
        databaseRepository.deleteArticle(article)
}