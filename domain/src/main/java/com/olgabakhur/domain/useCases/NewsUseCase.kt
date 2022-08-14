package com.olgabakhur.domain.useCases

import com.olgabakhur.domain.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsRepository.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsRepository.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article: com.olgabakhur.data.model.news.Article) =
        newsRepository.upsert(article)

    fun getSavedArticles() = newsRepository.getSavedArticles()

    suspend fun deleteArticle(article: com.olgabakhur.data.model.news.Article) =
        newsRepository.deleteArticle(article)
}