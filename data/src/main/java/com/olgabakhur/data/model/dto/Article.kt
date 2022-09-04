package com.olgabakhur.data.model.dto

import java.io.Serializable

data class Article(
    val url: String,
    val author: String,
    val content: String,
    val description: String,
    val publishDate: String,
    val sourceName: String,
    val title: String,
    val urlToImage: String,
    var isSaved: Boolean = false
) : Serializable