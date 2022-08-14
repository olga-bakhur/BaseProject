package com.olgabakhur.domain.repositoryImpl

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.local.newsDatabase.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.domain.repository.NewsRepository
import com.olgabakhur.domain.util.SafeApiCall
import com.olgabakhur.domain.util.result.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    articleDatabase: ArticleDatabase
) : NewsRepository {

    private var newsDao: ArticleDao = articleDatabase.getArticleDao()

    // News Api
    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem> =
        SafeApiCall.doSafeApiCall { newsApi.getBreakingNews(countryCode, pageNumber) }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem> =
        SafeApiCall.doSafeApiCall { newsApi.searchForNews(searchQuery, pageNumber) }

    // News database
    override suspend fun upsert(article: Article) = newsDao.upsert(article)

    override fun getSavedArticles() = newsDao.getAllArticles()

    override suspend fun deleteArticle(article: Article) = newsDao.deleteArticle(article)
}