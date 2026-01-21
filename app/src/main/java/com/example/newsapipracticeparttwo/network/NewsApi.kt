package com.example.newsapipracticeparttwo.network

import com.example.newsapipracticeparttwo.model.NewsData
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") county : String?,
        @Query("apiKey") apiKey : String?,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int = 20
    ) : NewsData

}