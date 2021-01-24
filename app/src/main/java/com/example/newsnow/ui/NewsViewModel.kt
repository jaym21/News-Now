package com.example.newsnow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.newsnow.apiModels.Article
import com.example.newsnow.apiModels.NewsResponse
import com.example.newsnow.fragments.LatestNews
import com.example.newsnow.repository.NewsRepository
import com.example.newsnow.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRespository: NewsRepository): ViewModel() {

    //getting the latestNews as mutableLiveData in the viewModel
    val latestNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var latestNewsPage = 1
    var latestNewsResponse: NewsResponse? = null

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


    //to handle the successful and error state of the response we get from the repository/api
    private fun handleLatestNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        //checking if we got a successful response
        if (response.isSuccessful) {
            //checking if body of response is not null
            response.body()?. let {
                //after getting the response(i.e first 20 articles) we increase the page no.
                latestNewsPage++
                //storing the response received from api
                if(latestNewsResponse == null) {
                    latestNewsResponse = it
                }else{
                    //if this is not the first 20 articles response received then we will add the new received articles to the previous articles saved
                    //getting the previous articles received earlier
                    val previousArticles = latestNewsResponse?.articles
                    //getting the new articles received from api
                    val newArticles = it.articles
                    //adding the new articles in the previous articles
                    previousArticles?.addAll(newArticles)
                }
                //returning the latestnewsresponse consisting all the articles received from api and if it is null then that means it is the first response received then returning the response received
                return Resource.Success(latestNewsResponse?: it)
            }
        }
        //when response is unsuccessful
        return Resource.Error(response.message())
    }


    //to save an article in saved news fragment
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRespository.insertSavedArticle(article)
    }

    //to delete an article saved in the saved news fragment
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRespository.deleteSavedArticle(article)
    }

    //to get all the saved articles from database to display in saved news fragment
    fun getSavedArticles() = newsRespository.getAllSavedNews()

}