package com.example.newsapipracticeparttwo.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapipracticeparttwo.model.ArticleEntity
import com.example.newsapipracticeparttwo.network.ArticleDAO

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDAO
}