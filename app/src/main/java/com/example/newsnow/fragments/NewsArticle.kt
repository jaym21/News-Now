package com.example.newsnow.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.newsnow.R
import com.example.newsnow.ui.NewsActivity
import kotlinx.android.synthetic.main.fragment_newsartcile.*


class NewsArticle: Fragment(R.layout.fragment_newsartcile) {

    lateinit var viewModel: ViewModel
    val args: NewsArticleArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        //getting the article passes as argument from other fragments
        val article = args.article

        //passing the url of the clicked article to webViewClient to view full article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }
}