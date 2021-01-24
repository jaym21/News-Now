package com.example.newsnow.apiModels

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)