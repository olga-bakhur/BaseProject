package com.olgabakhur.domain.repositoryImpl

import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.model.news.NewsItem
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.local.newsDatabase.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.domain.util.safeApiCall
import com.olgabakhur.domain.repository.NewsRepository
import com.olgabakhur.domain.util.result.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDatabase: ArticleDatabase
) : NewsRepository {

    private lateinit var newsDao: ArticleDao // TODO


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