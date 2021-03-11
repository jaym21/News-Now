package com.example.newsnow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnow.NewsRVAdapter
import com.example.newsnow.R
import com.example.newsnow.databinding.FragmentSavednewsBinding
import com.example.newsnow.ui.NewsActivity
import com.example.newsnow.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNews: Fragment(R.layout.fragment_savednews) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRVAdapter: NewsRVAdapter
    private var binding: FragmentSavednewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavednewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //initializing viewModel by casting this as NewsActivity so that we have access to the viewModel created in NewsActivity
        viewModel = (activity as NewsActivity).viewModel

        //setting recycler view
        setRecyclerView()

        //to set the functionality of touch direction on recycler view
        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //this will be empty as on up and down movement we do not need to do any thing with article
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //getting the position of the item swiped
                val position = viewHolder.adapterPosition
                //now getting the article from database using position in RV
                val article = newsRVAdapter.getArticle(position)
                //deleting the article using viewModel deleteArticle function
                viewModel.deleteArticle(article)
                //making a snackbar to show that article is deleted and giving an option to undo the delete
                Snackbar.make(view, "Article Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        //saving back the article in database that was deleted
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        //making the itemtouchhelper
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding?.rvSavedNews)
        }

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

        //observing the liveData of saved news to update the recycler view with any news article saved
        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            newsRVAdapter.updateRV(it)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setRecyclerView() {
        newsRVAdapter = NewsRVAdapter()
        binding?.rvSavedNews?.adapter = newsRVAdapter
        binding?.rvSavedNews?.layoutManager = LinearLayoutManager(activity)
    }
}