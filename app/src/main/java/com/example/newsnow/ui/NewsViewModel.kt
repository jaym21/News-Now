package com.example.newsnow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.newsnow.apiModels.NewsResponse
import com.example.newsnow.fragments.LatestNews
import com.example.newsnow.repository.NewsRepository
import com.example.newsnow.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRespository: NewsRepository): ViewModel() {

    //getting the latestNews as mutableLiveData in the viewModel
    val latestNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var latestNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getLatestNews("in")
    }

    fun getLatestNews(countryCode: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading sign
        latestNews.postValue(Resource.Loading())

        //getting response from repository
        val response = newsRespository.getLatestNews(countryCode, latestNewsPage)
        latestNews.postValue(handleLatestNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading sign
        searchNews.postValue(Resource.Loading())

        //getting response from repository
        val response = newsRespository.searchNews(searchQuery, searchNewsPage)
        latestNews.postValue(handleSearchNewsResponse(response))
    }

    //to handle the successful and error state of the response we get from the repository/api
    private fun handleLatestNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        //checking if we got a successful response
        if (response.isSuccessful) {
            //checking if body of response is not null
            response.body()?. let {
                return Resource.Success(it)
            }
        }
        //when response is unsuccessful
        return Resource.Error(response.message())
    }


    //to handle the successful and error state of the response we get from the repository/api
    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        //checking if we got a successful response
        if (response.isSuccessful) {
            //checking if body of response is not null
            response.body()?. let {
                return Resource.Success(it)
            }
        }
        //when response is unsuccessful
        return Resource.Error(response.message())
    }

}