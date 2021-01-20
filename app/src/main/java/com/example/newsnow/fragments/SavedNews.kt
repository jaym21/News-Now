package com.example.newsnow.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsnow.NewsRVAdapter
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_latestnews.*
import kotlinx.android.synthetic.main.fragment_savednews.*

class SavedNews: Fragment(R.layout.fragment_savednews) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRVAdapter: NewsRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting recycler view
        setRecyclerView()

        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel


        //making a bundle to pass article clicked to article fragment
        newsRVAdapter.setOnItemClickedListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                //passing the action of navigation from saved news fragment to article fragment
                R.id.action_savedNews_to_newsArticle,
                bundle
            )
        }

    }

    private fun setRecyclerView() {
        newsRVAdapter = NewsRVAdapter()
        rvSavedNews.adapter = newsRVAdapter
        rvSavedNews.layoutManager = LinearLayoutManager(activity)
    }
}