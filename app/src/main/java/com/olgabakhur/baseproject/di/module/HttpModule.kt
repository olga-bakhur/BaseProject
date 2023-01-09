package com.olgabakhur.baseproject.di.module

import com.olgabakhur.data.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object HttpModule {

    private const val TIME_OUT_5S = 5L

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                connectTimeout(TIME_OUT_5S, TimeUnit.SECONDS)
                readTimeout(TIME_OUT_5S, TimeUnit.SECONDS)
                writeTimeout(TIME_OUT_5S, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG)
                    addInterceptor(getHttpLoggingInterceptor())
            }
            .build()

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}