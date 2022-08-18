package com.olgabakhur.baseproject.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repository.UserPreferencesRepository
import com.olgabakhur.data.repositoryImpl.NewsRepositoryImpl
import com.olgabakhur.data.repositoryImpl.UserPreferencesRepositoryImpl
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseModule::class, DataStoreModule::class])
object RepositoriesModule {

    @Provides
    fun provideNewsRepository(newsApi: NewsApi, articleDao: ArticleDao): NewsRepository =
        NewsRepositoryImpl(newsApi, articleDao)

    @Provides
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepositoryImpl(dataStore)
}