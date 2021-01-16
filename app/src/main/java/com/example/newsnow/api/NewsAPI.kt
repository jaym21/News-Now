package com.example.newsnow.api

import com.example.newsnow.apiModels.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    //for getting top news
    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = "7d1d56f0fff74ec3b34c7ad794c8ce2b"
    ): Response<NewsResponse>

    //for searching news
    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = "7d1d56f0fff74ec3b34c7ad794c8ce2b"
    ): Response<NewsResponse>
}