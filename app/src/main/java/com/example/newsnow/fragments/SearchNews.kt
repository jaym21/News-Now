package com.example.newsnow.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnow.NewsRVAdapter
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel
import com.example.newsnow.util.Resource
import kotlinx.android.synthetic.main.fragment_latestnews.pbLoading
import kotlinx.android.synthetic.main.fragment_searchnews.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNews: Fragment(R.layout.fragment_searchnews) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRVAdapter: NewsRVAdapter
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel

        //setting recycler view
        setRecyclerView()

        //making a bundle to pass article clicked to article fragment
        newsRVAdapter.setOnItemClickedListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                //passing the action of navigation from search news fragment to article fragment
                R.id.action_searchNews_to_newsArticle,
                bundle
            )
        }


        //adding delay in search process so that it does not overload the app by making search request at every letter typed
        var job: Job? = null
        etSearch.addTextChangedListener {
            //when text is edited or typed first we cancel the job
            job?.cancel()
            //now start a another job in mainscope coroutine
            job = MainScope().launch {
                delay(500L)
                //first checking if edit text is not null
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        //now searching for the news by passing it through viewModel
                        viewModel.searchNews(it.toString())
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
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


    }
    private fun setRecyclerView() {
        newsRVAdapter = NewsRVAdapter()
        rvSearchNews.adapter = newsRVAdapter
        rvSearchNews.layoutManager = LinearLayoutManager(activity)
    }
}


