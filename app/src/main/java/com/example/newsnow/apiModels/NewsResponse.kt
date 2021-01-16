package com.example.newsnow.apiModels

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)