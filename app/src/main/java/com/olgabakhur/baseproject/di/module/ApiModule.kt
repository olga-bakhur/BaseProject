package com.olgabakhur.baseproject.di.module

import com.olgabakhur.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [HttpModule::class])
object ApiModule {

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)
}