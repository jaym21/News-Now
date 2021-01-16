package com.example.newsnow

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.bumptech.glide.Glide
import com.example.newsnow.apiModels.Article
import kotlinx.android.synthetic.main.item_news.view.*


//making a Adapter
class ListAdapter: RecyclerView.Adapter<ViewHolder>() {

    private val items: ArrayList<Article> = ArrayList<Article>()
    //this creates a view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //LayoutInflater is used to convert the itemview which is in 'xml' format to view format
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        val newsViewHolder = ViewHolder(view)

        return newsViewHolder
    }

    //counts how many items should be present
    override fun getItemCount(): Int {
        return items.size
    }

    //this binds the item with the data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.name.text = currentItem.source.name
        holder.time.text = currentItem.publishedAt
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.newsImage)

        //setting the onClickListener for news item in recycler view
        holder.itemView.setOnClickListener {
            onItemClickedListener?.let { it(currentItem) }
        }
    }
    

    //when an item is clicked we need to open the article in article fragment
    private var onItemClickedListener: ((Article) -> Unit)? = null

    fun setOnItemClickedListener(listener: (Article) -> Unit) {
        onItemClickedListener = listener
    }
}

//making a View Holder
//we add things that we want to display in that item
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.tvTitle)
    val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
    val  name: TextView = itemView.findViewById(R.id.tvName)
    val time: TextView = itemView.findViewById(R.id.tvTime)
}



