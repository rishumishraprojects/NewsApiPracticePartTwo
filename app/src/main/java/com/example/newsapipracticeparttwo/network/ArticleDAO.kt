package com.example.newsapipracticeparttwo.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapipracticeparttwo.model.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles : List<ArticleEntity>)

    @Query("Select * FROM articles LIMIT :loadSize")
    fun getPagedArticles(loadSize: Int): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()

}