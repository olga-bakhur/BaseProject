package com.olgabakhur.data.repository

import com.olgabakhur.data.model.dto.Article
import com.olgabakhur.data.model.dto.UserCredentials
import com.olgabakhur.data.util.result.Result

interface NewsRepository {

    suspend fun signIn(email: String, password: String): Result<UserCredentials>
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<List<Article>>
}