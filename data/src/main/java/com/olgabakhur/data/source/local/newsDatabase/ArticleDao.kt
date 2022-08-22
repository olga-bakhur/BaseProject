package com.olgabakhur.data.source.local.newsDatabase

import androidx.room.*
import com.olgabakhur.data.model.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticle(article: ArticleEntity): Int
}