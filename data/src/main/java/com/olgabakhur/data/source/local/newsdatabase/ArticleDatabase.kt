package com.olgabakhur.data.source.local.newsdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olgabakhur.data.model.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
/* @TypeConverters(Converters::class) */
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article_db.db"

        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}