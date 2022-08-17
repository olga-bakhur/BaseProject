package com.olgabakhur.data.source.remote

import com.olgabakhur.data.BuildConfig
import com.olgabakhur.data.model.news.auth.AuthRequest
import com.olgabakhur.data.model.news.auth.AuthResponse
import com.olgabakhur.data.model.news.pojo.NewsItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {

    @POST("session/password")
    suspend fun signIn(
        @Body authRequest: AuthRequest
    ): AuthResponse

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsItem

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsItem
}