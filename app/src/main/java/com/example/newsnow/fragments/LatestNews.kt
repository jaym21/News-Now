package com.example.newsnow.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnow.NewsRVAdapter
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel
import com.example.newsnow.util.Resource
import kotlinx.android.synthetic.main.fragment_latestnews.*

//response page size
const val PAGE_SIZE_QUERY = 20

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

        //making a bundle to pass article clicked to article fragment
        newsRVAdapter.setOnItemClickedListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                //passing the action of navigation from latest news fragment to article fragment
                R.id.action_latestNews_to_newsArticle,
                bundle
            )
        }



        //making the viewModel observer on latestNews mutableLiveData for LiveData changes
        viewModel.latestNews.observe(viewLifecycleOwner, Observer { response ->
                //handling the responses using Resource classes
            when(response) {
                //when we get a successful response hide loading sign
                is Resource.Success -> {
                    pbLoading.visibility = View.INVISIBLE
                    isLoading = false
                    response.data?. let {
                        newsRVAdapter.updateRV(it.articles.toList())
                        //checking if the currently received response is the last page of response so that we can paginate
                        //getting the total pages of response
                        val totalPages = it.totalResults / PAGE_SIZE_QUERY + 2 //adding 2 as integer div is always floor rounded off so 1 is added and another 1 is is added as last page of response is empty so to take that in account
                        //setting boolean isLastPage according to the current page no
                        isLastPage = viewModel.latestNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
                    pbLoading.visibility = View.INVISIBLE
                    isLoading = false
                    response.responseMessage?. let {
                        Log.e(TAG, "Error in response: $it")
                    }
                }
                is Resource.Loading -> {
                    pbLoading.visibility = View.VISIBLE
                    isLoading = true
                }

            }
        })
    }

    //handling pagination
    var isScrolling = false
    var isLastPage = false
    var isLoading = false

    //adding a scroll listener on recycler view
    val paginationScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            //to know if user has scrolled at the last page(bottom of recycler view) we need to calculate it through layout manager
            //getting the linear layout manager from recycler view
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            //now getting the first item position which is visible on page
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            //getting the total visible items count on page(i.e the count of the no.of items loaded in recycler view till that point)
            val visibleItemCount = layoutManager.childCount

            //getting the total items count in recycler view
            val totalItemsCount = layoutManager.itemCount

            //checking if are not at the last page or are not loading
            val isNotAtLastPageAndNotLoading = !isLastPage && !isLoading

            //checking if user is at the last page of the response
            //on adding the first item's position with the no.of items loaded if it is greater than or equal to no.of items in recycler view that means user is at last page
            val isAtLastPage = firstVisibleItemPosition + visibleItemCount >= totalItemsCount

            //checking if user has scrolled from the first page
            val notAtBeginning = firstVisibleItemPosition >= 0

            //checking if the no.of items in 1 query response are all loaded in recycler view
            val isTotalMoreThanVisible = totalItemsCount >= PAGE_SIZE_QUERY

            //now creating a boolean to know if to paginate or not
            val shouldPaginate = isNotAtLastPageAndNotLoading && isAtLastPage && isTotalMoreThanVisible && notAtBeginning && isScrolling

            if(shouldPaginate) {
                //makes another request to the api and gets next 20 news articles
                viewModel.getLatestNews("in")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //checking if the user is scrolling by checking the state is equal to scroll touch state
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setRecyclerView() {
        newsRVAdapter = NewsRVAdapter()
        rvLatestNews.adapter = newsRVAdapter
        rvLatestNews.layoutManager = LinearLayoutManager(activity)
        rvLatestNews.addOnScrollListener(paginationScrollListener)
    }
}