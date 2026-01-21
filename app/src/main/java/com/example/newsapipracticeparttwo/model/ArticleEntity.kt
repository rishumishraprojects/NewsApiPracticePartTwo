package com.example.newsapipracticeparttwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class ArticleEntity(

    @PrimaryKey
    val url : String,
    val title : String,
    val urlToImage : String

)
