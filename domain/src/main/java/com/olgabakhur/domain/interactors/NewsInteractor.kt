package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.domain.useCases.NewsUseCase
import com.olgabakhur.domain.util.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val newsUseCase: NewsUseCase
) {

    // News Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem> =
        newsUseCase.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem> =
        newsUseCase.searchNews(searchQuery, pageNumber)

    // News database
    suspend fun insertArticle(article: Article): Result<Long> =
        newsUseCase.insertArticle(article)

    suspend fun getSavedArticles(): Result<Flow<List<Article>>> =
        newsUseCase.getSavedArticles()

    suspend fun deleteArticle(article: Article): Result<Int> =
        newsUseCase.deleteArticle(article)
}