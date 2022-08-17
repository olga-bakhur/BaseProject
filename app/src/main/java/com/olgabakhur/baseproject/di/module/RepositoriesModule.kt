package com.olgabakhur.baseproject.di.module

import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repositoryImpl.NewsRepositoryImpl
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseModule::class])
object RepositoriesModule {

    @Provides
    fun provideNewsRepository(
        newsApi: NewsApi,
        articleDao: ArticleDao
    ): NewsRepository =
        NewsRepositoryImpl(newsApi, articleDao)
}