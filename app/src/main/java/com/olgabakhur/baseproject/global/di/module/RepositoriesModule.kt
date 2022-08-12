package com.olgabakhur.baseproject.global.di.module

import com.olgabakhur.data.repositoryImpl.NewsRepositoryImpl
import com.olgabakhur.data.source.local.db.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.data.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseModule::class])
object RepositoriesModule {

    @Provides
    fun provideNewsRepository(
        newsApi: NewsApi,
        articleDatabase: ArticleDatabase
    ): NewsRepository =
        NewsRepositoryImpl(newsApi, articleDatabase)
}