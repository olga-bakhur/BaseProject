package com.olgabakhur.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val urlToArticle: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publicationDate: String?,
    val sourceName: String?,
    val title: String?,
    val urlToImage: String?
) : Parcelable