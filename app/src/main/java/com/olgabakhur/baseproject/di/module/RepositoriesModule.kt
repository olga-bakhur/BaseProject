package com.olgabakhur.baseproject.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.olgabakhur.data.repository.ConnectivityRepository
import com.olgabakhur.data.repository.DatabaseRepository
import com.olgabakhur.data.repository.NewsRepository
import com.olgabakhur.data.repository.PreferencesRepository
import com.olgabakhur.data.repositoryimpl.ConnectivityRepositoryImpl
import com.olgabakhur.data.repositoryimpl.DatabaseRepositoryImpl
import com.olgabakhur.data.repositoryimpl.NewsRepositoryImpl
import com.olgabakhur.data.repositoryimpl.PreferencesRepositoryImpl
import com.olgabakhur.data.source.local.newsdatabase.ArticleDao
import com.olgabakhur.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named

@Module(includes = [DatabaseModule::class, DataStoreModule::class])
object RepositoriesModule {

    @Provides
    fun provideNewsRepository(api: NewsApi): NewsRepository =
        NewsRepositoryImpl(api)

    @Provides
    fun provideDatabaseRepository(
        dao: ArticleDao
    ): DatabaseRepository = DatabaseRepositoryImpl(dao)

    @Provides
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository =
        PreferencesRepositoryImpl(dataStore)

    @Provides
    fun provideConnectivityRepository(
        @Named("ClientForWebSocket") okHttpClient: OkHttpClient
    ): ConnectivityRepository = ConnectivityRepositoryImpl(okHttpClient)

}