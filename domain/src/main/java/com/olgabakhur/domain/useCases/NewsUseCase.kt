package com.olgabakhur.domain.useCases

import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    // News Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem> =
        newsRepository.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem> =
        newsRepository.searchNews(searchQuery, pageNumber)

    // News database
    suspend fun insertArticle(article: Article): Result<Long> =
        newsRepository.insertArticle(article)

    suspend fun getSavedArticles(): Result<Flow<List<Article>>> =
        newsRepository.getSavedArticles()

    suspend fun deleteArticle(article: Article): Result<Int> =
        newsRepository.deleteArticle(article)
}