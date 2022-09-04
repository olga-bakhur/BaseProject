package com.olgabakhur.data.repositoryimpl

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.model.pojo.SignInRequest
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.util.mappers.news.toArticlesList
import com.olgabakhur.data.util.mappers.news.toUserCredentials
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.safeCall.SafeApiCall
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

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
}