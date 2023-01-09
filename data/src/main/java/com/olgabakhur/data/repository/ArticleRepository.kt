package com.olgabakhur.data.repository

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.util.result.Result

interface ArticleRepository {

    suspend fun getArticlesList(countryCode: String, pageNumber: Int): Result<List<Article>>
}