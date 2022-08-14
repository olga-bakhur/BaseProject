package com.olgabakhur.baseproject.di.module

import com.olgabakhur.domain.repositoryImpl.NewsRepositoryImpl
import com.olgabakhur.data.source.local.newsDatabase.ArticleDatabase
import com.olgabakhur.data.source.remote.NewsApi
import com.olgabakhur.domain.repository.NewsRepository
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