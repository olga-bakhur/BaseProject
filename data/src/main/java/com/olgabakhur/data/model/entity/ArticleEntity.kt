package com.olgabakhur.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Articles")
data class ArticleEntity(
    @PrimaryKey
    val urlToArticle: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publicationDate: String?,
    val sourceName: String?,
    val title: String?,
    val urlToImage: String?
)