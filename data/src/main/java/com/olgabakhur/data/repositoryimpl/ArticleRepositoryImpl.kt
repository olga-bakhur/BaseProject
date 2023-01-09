package com.olgabakhur.data.repositoryimpl

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.repository.ArticleRepository
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.util.mappers.news.toListArticles
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.safeCall.SafeIoCall
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : ArticleRepository {

    override suspend fun getArticlesList(
        countryCode: String,
        pageNumber: Int
    ): Result<List<Article>> =
        SafeIoCall.doSafeIoCall {
            newsApi.getArticlesList(countryCode, pageNumber).toListArticles()
        }
}