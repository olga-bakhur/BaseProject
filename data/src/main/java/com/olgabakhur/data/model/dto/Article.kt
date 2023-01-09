package com.olgabakhur.data.model.dto

import java.io.Serializable

data class Article(
    val urlToArticle: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publicationDate: String?,
    val sourceName: String?,
    val title: String?,
    val urlToImage: String?
) : Serializable