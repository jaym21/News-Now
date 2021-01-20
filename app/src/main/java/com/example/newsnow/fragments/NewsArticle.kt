package com.example.newsnow.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel


class NewsArticle: Fragment(R.layout.fragment_newsartcile) {

    lateinit var viewModel: NewsViewModel
    //to get the article passed as argument from latestNews, savedNews and searchNews fragments
    val args: NewsArticleArgs by navArgs()
    private lateinit var webView: WebView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel
        //getting the article from argument
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }
}