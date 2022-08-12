package com.olgabakhur.baseproject.global.di.module

import android.app.Application
import androidx.room.Room
import com.olgabakhur.data.source.local.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, ArticleDatabase.DATABASE_NAME)
            .build()
    }
}