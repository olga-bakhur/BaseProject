package com.olgabakhur.data.repositoryImpl

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.data.source.local.db.ArticleDao
import com.olgabakhur.data.source.local.db.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.util.safeApiCall
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDatabase: ArticleDatabase
) : NewsRepository {

    private lateinit var newsDao: ArticleDao

    init {
        newsDao = articleDatabase.getArticleDao()
    }

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<NewsItem> =
        safeApiCall { newsApi.getBreakingNews(countryCode, pageNumber) }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<NewsItem> =
        safeApiCall { newsApi.searchForNews(searchQuery, pageNumber) }

    override suspend fun upsert(article: Article) = newsDao.upsert(article)

    override fun getSavedArticles() = newsDao.getAllArticles()

    override suspend fun deleteArticle(article: Article) =
        newsDao.deleteArticle(article)
}