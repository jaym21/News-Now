package com.example.newsnow.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnow.NewsRVAdapter
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel
import com.example.newsnow.util.Resource
import kotlinx.android.synthetic.main.fragment_latestnews.*

class LatestNews: Fragment(R.layout.fragment_latestnews) {

    val TAG = "LatestNewsFragment"
    lateinit var viewModel: NewsViewModel
    lateinit var newsRVAdapter: NewsRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel

        //setting recyclerView
        setRecyclerView()

        //making the viewModel observer on latestNews mutableLiveData for LiveData changes
        viewModel.latestNews.observe(viewLifecycleOwner, Observer { response ->
            //handling the responses using Resource classes
            when(response) {
                //when we get a successful response hide loading sign
                is Resource.Success -> {
                    pbLoading.visibility = View.INVISIBLE
                    response.data?. let {
                        newsRVAdapter.updateRV(it.articles)
                    }
                }
                is Resource.Error -> {
                    pbLoading.visibility = View.INVISIBLE
                    response.responseMessage?. let {
                        Log.e(TAG, "Error in response: $it")
                    }
                }
                is Resource.Loading -> {
                    pbLoading.visibility = View.VISIBLE
                }

            }
        })
    }

    private fun setRecyclerView() {
        newsRVAdapter = NewsRVAdapter()
        rvLatestNews.adapter = newsRVAdapter
        rvLatestNews.layoutManager = LinearLayoutManager(activity)
    }
}