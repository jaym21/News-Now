package com.example.newsnow.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit singleton class to make request to api from anywhere
class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            val BASE_URL = "https://newsapi.org"

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        //api object which will be used to make api request from anywhere in the code
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}