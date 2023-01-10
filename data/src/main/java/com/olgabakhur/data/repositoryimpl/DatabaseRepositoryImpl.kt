package com.olgabakhur.data.repositoryimpl

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.repository.DatabaseRepository
import com.olgabakhur.data.source.local.ArticleDao
import com.olgabakhur.data.util.mappers.toArticle
import com.olgabakhur.data.util.mappers.toArticleEntity
import com.olgabakhur.data.util.result.Result
import com.olgabakhur.data.util.safecall.SafeIoCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
) : DatabaseRepository {

    override suspend fun getSavedArticlesList(): Result<Flow<List<Article>>> =
        SafeIoCall.doSafeIoCall {
            articleDao.getSavedArticlesList()
                .map { listArticleEntity ->
                    listArticleEntity.map { articleEntity ->
                        articleEntity.toArticle()
                    }
                }
        }

    override suspend fun saveArticle(article: Article): Result<Unit> =
        SafeIoCall.doSafeIoCall {
            articleDao.saveArticle(article.toArticleEntity())
        }

    override suspend fun deleteArticle(article: Article): Result<Unit> =
        SafeIoCall.doSafeIoCall {
            articleDao.deleteArticle(article.toArticleEntity())
        }
}