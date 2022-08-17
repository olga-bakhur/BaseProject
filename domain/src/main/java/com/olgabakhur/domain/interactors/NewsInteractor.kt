package com.olgabakhur.domain.interactors

import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.domain.useCases.NewsUseCase
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