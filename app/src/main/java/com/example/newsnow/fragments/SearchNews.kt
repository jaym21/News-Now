package com.example.newsnow.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel

class SearchNews: Fragment(R.layout.fragment_searchnews) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel
    }
}