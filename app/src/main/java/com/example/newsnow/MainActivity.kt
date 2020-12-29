package com.example.newsnow

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {

    //for navigation drawer
    lateinit var toggle: ActionBarDrawerToggle

    //mAdapter means making ListAdapter a member function i.e making to available to access from anywhere in the code
    private lateinit var mAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //making the drawer toggle
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.headlines -> fetchData()
                R.id.science -> fetchDataScience()
                R.id.health -> fetchDataHealth()
                R.id.sports -> fetchDataSports()
                R.id.entertainment -> fetchDataEntertainment()
            }
            true
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = ListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    //to respond to clicks on the drawer button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=7d1d56f0fff74ec3b34c7ad794c8ce2b"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")

                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJsonObject.getJSONObject("source")
                    // parsing of data from url
                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                //passing the parsed news data to make a news item
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        //to make the api work
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    private fun fetchDataScience() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=7d1d56f0fff74ec3b34c7ad794c8ce2b"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")

                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJsonObject.getJSONObject("source")
                    // parsing of data from url
                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                //passing the parsed news data to make a news item
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        //to make the api work
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    private fun fetchDataHealth() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=7d1d56f0fff74ec3b34c7ad794c8ce2b"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")

                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJsonObject.getJSONObject("source")
                    // parsing of data from url
                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                //passing the parsed news data to make a news item
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        //to make the api work
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun fetchDataSports() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=7d1d56f0fff74ec3b34c7ad794c8ce2b"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")

                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJsonObject.getJSONObject("source")
                    // parsing of data from url
                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                //passing the parsed news data to make a news item
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        //to make the api work
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun fetchDataEntertainment() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=7d1d56f0fff74ec3b34c7ad794c8ce2b"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")

                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJsonObject.getJSONObject("source")
                    // parsing of data from url
                    val news = News(
                        newsJsonObject.getString("title"),
                        sourceObject.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                //passing the parsed news data to make a news item
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        //to make the api work
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    override fun onItemClicked(item: News) {
         val builder = CustomTabsIntent.Builder()
         val customTabsIntent = builder.build()
         customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

}
