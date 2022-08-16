package com.olgabakhur.data.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Articles")
data class Article(
    @PrimaryKey
    val url: String = "",
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
) : Serializable