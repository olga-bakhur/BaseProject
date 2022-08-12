package com.olgabakhur.baseproject.global.di.module

import com.olgabakhur.baseproject.global.util.Constants.TIME_OUT_10S
import com.olgabakhur.data.BuildConfig
import com.olgabakhur.data.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object HttpModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .apply {
                connectTimeout(TIME_OUT_10S, TimeUnit.SECONDS)
                readTimeout(TIME_OUT_10S, TimeUnit.SECONDS)
                writeTimeout(TIME_OUT_10S, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG)
                    addInterceptor(interceptor)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}