package com.olgabakhur.baseproject.di.module

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.olgabakhur.data.repository.ArticleRepository
import com.olgabakhur.data.repository.DatabaseRepository
import com.olgabakhur.data.repository.NetworkConnectivityObserver
import com.olgabakhur.data.repository.PreferencesRepository
import com.olgabakhur.data.repositoryimpl.ArticleRepositoryImpl
import com.olgabakhur.data.repositoryimpl.DatabaseRepositoryImpl
import com.olgabakhur.data.repositoryimpl.NetworkConnectivityObserverImpl
import com.olgabakhur.data.repositoryimpl.PreferencesRepositoryImpl
import com.olgabakhur.data.source.local.ArticleDao
import com.olgabakhur.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseModule::class, DataStoreModule::class])
object RepositoriesModule {

    @Provides
    fun provideArticleRepository(api: NewsApi): ArticleRepository = ArticleRepositoryImpl(api)

    @Provides
    fun provideDatabaseRepository(
        dao: ArticleDao
    ): DatabaseRepository = DatabaseRepositoryImpl(dao)

    @Provides
    fun preferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository =
        PreferencesRepositoryImpl(dataStore)

    @Provides
    fun provideNetworkConnectivityObserver(
        app: Application
    ): NetworkConnectivityObserver =
        NetworkConnectivityObserverImpl(app.applicationContext)
}