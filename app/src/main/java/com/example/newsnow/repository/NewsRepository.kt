package com.example.newsnow.repository

import com.example.newsnow.api.RetrofitInstance
import com.example.newsnow.database.NewsArticleDatabase

class NewsRepository( val database: NewsArticleDatabase) {

    //getting the latest news by making an api call through the retrofit instance passing the countryCode and pageNumber
    //it is suspend fun as we are using coroutines
    suspend fun getLatestNews(countryCode: String, pageNumber: Int) = RetrofitInstance.api.getLatestNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) = RetrofitInstance.api.searchNews(searchQuery, pageNumber)
}