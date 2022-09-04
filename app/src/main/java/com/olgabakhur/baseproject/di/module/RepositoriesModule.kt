package com.olgabakhur.baseproject.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repository.UserPreferencesRepository
import com.olgabakhur.data.repositoryimpl.NewsRepositoryImpl
import com.olgabakhur.data.repositoryimpl.UserPreferencesRepositoryImpl
import com.olgabakhur.data.source.local.newsdatabase.ArticleDao
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