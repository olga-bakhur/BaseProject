package com.olgabakhur.data.repositoryImpl

import com.olgabakhur.data.model.news.auth.AuthRequest
import com.olgabakhur.data.model.news.auth.AuthResponse
import com.olgabakhur.data.model.news.pojo.Article
import com.olgabakhur.data.model.news.pojo.NewsItem
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.local.newsDatabase.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.util.safeCall.SafeApiCall
import com.olgabakhur.data.util.safeCall.SafeDatabaseCall
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    articleDatabase: ArticleDatabase
) : NewsRepository {

    private var newsDao: ArticleDao = articleDatabase.getArticleDao()

    // News Api
    override suspend fun signIn(authRequest: AuthRequest): Result<AuthResponse> =
        SafeApiCall.doSafeApiCall { newsApi.signIn(authRequest) }

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem> =
        SafeApiCall.doSafeApiCall { newsApi.getBreakingNews(countryCode, pageNumber) }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem> =
        SafeApiCall.doSafeApiCall { newsApi.searchForNews(searchQuery, pageNumber) }

    // News database
    override suspend fun insertArticle(article: Article): Result<Long> =
        SafeDatabaseCall.doSafeDatabaseCall { newsDao.insertArticle(article) }

    override suspend fun getSavedArticles(): Result<Flow<List<Article>>> =
        SafeDatabaseCall.doSafeDatabaseCall { newsDao.getAllArticles() }

    override suspend fun deleteArticle(article: Article): Result<Int> =
        SafeDatabaseCall.doSafeDatabaseCall { newsDao.deleteArticle(article) }
}