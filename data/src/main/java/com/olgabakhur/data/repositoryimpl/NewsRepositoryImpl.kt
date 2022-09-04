package com.olgabakhur.data.repositoryimpl

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.model.pojo.SignInRequest
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.source.local.newsdatabase.ArticleDao
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.util.mappers.news.toArticle
import com.olgabakhur.data.util.mappers.news.toArticleEntity
import com.olgabakhur.data.util.mappers.news.toArticlesList
import com.olgabakhur.data.util.mappers.news.toUserCredentials
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.safeCall.SafeApiCall
import com.olgabakhur.data.util.safeCall.SafeDatabaseCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) : NewsRepository {

    // News Api
    override suspend fun signIn(email: String, password: String): Result<UserCredentials> =
        SafeApiCall.doSafeApiCall {
            newsApi.signIn(
                SignInRequest(
                    email = email,
                    password = password
                )
            ).toUserCredentials()
        }

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Result<List<Article>> =
        SafeApiCall.doSafeApiCall {
            newsApi.getBreakingNews(countryCode, pageNumber).toArticlesList()
        }

    // News database
    override suspend fun insertArticle(article: Article): Result<Long> =
        SafeDatabaseCall.doSafeDatabaseCall {
            articleDao.insertArticle(article.toArticleEntity())
        }

    override suspend fun getSavedArticles(): Result<Flow<List<Article>>> =
        SafeDatabaseCall.doSafeDatabaseCall {
            articleDao.getAllArticles()
                .map { list ->
                    list.map { entity ->
                        entity.toArticle()
                    }
                }
        }

    override suspend fun deleteArticle(article: Article): Result<Int> =
        SafeDatabaseCall.doSafeDatabaseCall {
            articleDao.deleteArticle(article.toArticleEntity())
        }
}