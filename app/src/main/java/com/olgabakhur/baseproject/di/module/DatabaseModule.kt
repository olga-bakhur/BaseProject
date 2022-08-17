package com.olgabakhur.baseproject.di.module

import android.app.Application
import androidx.room.Room
import com.olgabakhur.data.source.local.newsDatabase.ArticleDao
import com.olgabakhur.data.source.local.newsDatabase.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDao(app: Application): ArticleDao {
        val database = Room.databaseBuilder(
            app, ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).build()

        return database.getArticleDao()
    }
}