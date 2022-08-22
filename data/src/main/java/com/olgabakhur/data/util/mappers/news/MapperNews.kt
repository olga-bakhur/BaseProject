package com.olgabakhur.data.util.mappers.news

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.model.entity.ArticleEntity
import com.olgabakhur.data.model.pojo.ArticleResponse
import com.olgabakhur.data.model.pojo.NewsItemResponse
import com.olgabakhur.data.model.pojo.SignInResponse
import com.olgabakhur.data.util.Constants

fun SignInResponse.toUserCredentials() = UserCredentials(
    email = email,
    accountType = accountType,
    sessionId = sessionId
)

fun ArticleResponse.toArticle() = Article(
    url = url ?: Constants.EMPTY,
    author = author ?: Constants.EMPTY,
    content = content ?: Constants.EMPTY,
    description = description ?: Constants.EMPTY,
    publishedAt = publishedAt ?: Constants.EMPTY,
    sourceName = sourceResponse?.name ?: Constants.EMPTY,
    title = title ?: Constants.EMPTY,
    urlToImage = urlToImage ?: Constants.EMPTY
)

fun Article.toArticleEntity() = ArticleEntity(
    url = url,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    sourceName = sourceName,
    title = title,
    urlToImage = urlToImage
)

fun ArticleEntity.toArticle() = Article(
    url = url,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    sourceName = sourceName,
    title = title,
    urlToImage = urlToImage
)

fun NewsItemResponse.toArticlesList() =
    articleResponses.map { articleResponse: ArticleResponse ->
        articleResponse.toArticle()
    }