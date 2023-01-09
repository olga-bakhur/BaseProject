package com.olgabakhur.data.util.mappers.news

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.entity.ArticleEntity
import com.olgabakhur.data.model.pojo.ArticleResponse
import com.olgabakhur.data.model.pojo.NewsResponse
import java.util.*

/* API */
fun NewsResponse.toListArticles(): List<Article> =
    listArticlesResponse.map { articleResponse ->
        articleResponse.toArticle()
    }

fun ArticleResponse.toArticle() = Article(
    urlToArticle = urlToArticle ?: UUID.randomUUID().toString().substring(0, 15),
    author = author,
    content = content,
    description = description,
    publicationDate = publicationDate,
    sourceName = sourceResponse?.sourceName,
    title = title,
    urlToImage = urlToImage
)

/* Database */
fun Article.toArticleEntity() = ArticleEntity(
    urlToArticle = urlToArticle,
    author = author,
    content = content,
    description = description,
    publicationDate = publicationDate,
    sourceName = sourceName,
    title = title,
    urlToImage = urlToImage
)

fun ArticleEntity.toArticle() = Article(
    urlToArticle = urlToArticle,
    author = author,
    content = content,
    description = description,
    publicationDate = publicationDate,
    sourceName = sourceName,
    title = title,
    urlToImage = urlToImage
)