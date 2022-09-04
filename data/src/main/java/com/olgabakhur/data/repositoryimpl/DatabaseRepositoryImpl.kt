package com.olgabakhur.data.repositoryimpl

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.repository.DatabaseRepository
import com.olgabakhur.data.source.local.newsdatabase.ArticleDao
import com.olgabakhur.data.util.mappers.news.toArticle
import com.olgabakhur.data.util.mappers.news.toArticleEntity
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.safeCall.SafeDatabaseCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
) : DatabaseRepository {

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