package com.olgabakhur.baseproject.di.module

import android.app.Application
import androidx.room.Room
import com.olgabakhur.data.source.local.ArticleDao
import com.olgabakhur.data.source.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(app: Application): ArticleDatabase =
        Room.databaseBuilder(
            app, ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).build()

    @Provides
    fun provideArticleDao(database: ArticleDatabase): ArticleDao = database.getArticleDao()
}