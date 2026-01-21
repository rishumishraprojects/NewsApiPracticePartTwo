package com.example.newsapipracticeparttwo.di

import android.content.Context
import androidx.room.Room
import com.example.newsapipracticeparttwo.network.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit) : NewsApi{
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : NewsDatabase{
            return Room.databaseBuilder(
                context = context,
                NewsDatabase::class.java,
                "news_database"
            )
                .build()
    }









}