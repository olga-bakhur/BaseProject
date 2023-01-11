package com.olgabakhur.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String?,
    val content: String?,
    val sourceName: String?,
    val publicationDate: String?,
    val urlToArticle: String,
    val urlToImage: String?
) : Parcelable