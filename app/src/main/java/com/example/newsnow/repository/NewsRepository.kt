package com.example.newsnow.repository

import com.example.newsnow.api.RetrofitInstance
import com.example.newsnow.apiModels.Article
import com.example.newsnow.database.NewsArticleDatabase

class NewsRepository( val database: NewsArticleDatabase) {

    //getting the latest news by making an api call through the retrofit instance passing the countryCode and pageNumber
    //it is suspend fun as we are using coroutines
    suspend fun getLatestNews(countryCode: String, pageNumber: Int) = RetrofitInstance.api.getLatestNews(countryCode, pageNumber)

    //to insert an saved article in database using insert function in DAO
    suspend fun insertSavedArticle(article: Article) = database.getNewsArticleDAO().insert(article)

    //to get all saved news articles which stored as live data from database to display in saved news fragment
    fun getAllSavedNews() = database.getNewsArticleDAO().getAllNews()

    //to delete an saved article in database using delete function in DAO
    suspend fun deleteSavedArticle(article: Article) = database.getNewsArticleDAO().delete(article)
}