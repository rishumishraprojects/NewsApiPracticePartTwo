package com.example.newsapipracticeparttwo.model

data class Article(
    val title : String?,
    val url : String?,
    val urlToImage : String?
)


data class NewsData(
    val articles : List<Article>,
    val totalResults : Int
)