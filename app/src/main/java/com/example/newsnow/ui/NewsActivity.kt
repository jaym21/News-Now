package com.example.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsnow.R
import com.example.newsnow.database.NewsArticleDatabase
import com.example.newsnow.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(NewsArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        //initializing viewModel through viewModelProvider
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        bottomNavView.setupWithNavController(navHostFragment.findNavController())

    }
}
