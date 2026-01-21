package com.example.newsapipracticeparttwo.repository

import androidx.room.withTransaction
import com.example.newsapipracticeparttwo.di.NewsDatabase
import com.example.newsapipracticeparttwo.model.ArticleEntity
import com.example.newsapipracticeparttwo.network.NewsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi : NewsApi,
    private val newsDatabase : NewsDatabase
) {

    private val dao = newsDatabase.articleDao()

    fun getArticlesFromDatabase(limit: Int): Flow<List<ArticleEntity>> {
        return dao.getPagedArticles(limit)
    }

    suspend fun getNewsFromApi(page : Int) {
        val response = newsApi.getNews(
            county = "us",
            apiKey = "e4df13dccd3a4b978a0c4472f48ab3a0",
            page = page
        )


        val articleEntities = response.articles.map{ it ->
            ArticleEntity(
                url = it.url ?: " ",
                title = it.title ?: " ",
                urlToImage = it.urlToImage ?: " "
            )
        }

        newsDatabase.withTransaction{
            dao.insertArticles(articleEntities)
        }

    }
}