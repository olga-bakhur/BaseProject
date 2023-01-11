package com.olgabakhur.data.util.mappers

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.entity.ArticleEntity
import com.olgabakhur.data.model.pojo.ArticleResponse
import com.olgabakhur.data.model.pojo.NewsResponse
import java.util.*

/* API */
fun NewsResponse.toListArticles(): List<Article> =
    articleResponseList.map { articleResponse ->
        articleResponse.toArticle()
    }

fun ArticleResponse.toArticle() = Article(
    title = title,
    content = content,
    sourceName = sourceResponse?.sourceName,
    publicationDate = publicationDate,
    urlToArticle = urlToArticle ?: UUID.randomUUID().toString().substring(0, 15),
    urlToImage = urlToImage
)

/* Database */
fun Article.toArticleEntity() = ArticleEntity(
    title = title,
    content = content,
    sourceName = sourceName,
    publicationDate = publicationDate,
    urlToArticle = urlToArticle,
    urlToImage = urlToImage
)

fun ArticleEntity.toArticle() = Article(
    title = title,
    content = content,
    sourceName = sourceName,
    publicationDate = publicationDate,
    urlToArticle = urlToArticle,
    urlToImage = urlToImage
)