package com.olgabakhur.baseproject.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [HttpModule::class])
object ApiModule {

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): com.olgabakhur.data.source.remote.NewsApi =
        retrofit.create(com.olgabakhur.data.source.remote.NewsApi::class.java)
}