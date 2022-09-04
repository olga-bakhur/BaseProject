package com.olgabakhur.data.source.remote

import com.olgabakhur.data.BuildConfig
import com.olgabakhur.data.model.pojo.NewsItemResponse
import com.olgabakhur.data.model.pojo.SignInRequest
import com.olgabakhur.data.model.pojo.SignInResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {

    /* Fake request */
    @POST("session/password")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): SignInResponse

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsItemResponse
}