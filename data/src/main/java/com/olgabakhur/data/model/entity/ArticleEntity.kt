package com.olgabakhur.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Articles")
data class ArticleEntity(
    val title: String?,
    val content: String?,
    val sourceName: String?,
    val publicationDate: String?,
    @PrimaryKey
    val urlToArticle: String,
    val urlToImage: String?
)