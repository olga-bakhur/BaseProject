package com.olgabakhur.domain.interactors

import com.olgabakhur.domain.useCases.NewsUseCase
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val newsUseCase: NewsUseCase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsUseCase.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsUseCase.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article: com.olgabakhur.data.model.news.Article) =
        newsUseCase.upsert(article)

    fun getSavedArticles() = newsUseCase.getSavedArticles()

    suspend fun deleteArticle(article: com.olgabakhur.data.model.news.Article) =
        newsUseCase.deleteArticle(article)
}