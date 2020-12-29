package com.example.newsnow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.bumptech.glide.Glide



//making a Adapter
class ListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<ViewHolder>() {

    private val items: ArrayList<News> = ArrayList<News>()
    //this creates a view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //LayoutInflater is used to convert the itemview which is in 'xml' format to view format
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        val newsViewHolder = ViewHolder(view)
        //handling when any item is clicked
        view.setOnClickListener{
            listener.onItemClicked(items[newsViewHolder.adapterPosition])
        }
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
        holder.name.text = currentItem.name
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.newsImage)
    }

    //to add new news to the items array
    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        //to notify to again carry out all above functions to make a new news item
        notifyDataSetChanged()
    }
}

//making a View Holder
//we add things that we want to display in that item
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
    val  name: TextView = itemView.findViewById(R.id.name)
}

//making an interface to communicate to adapter that an item is clicked
interface NewsItemClicked {
    fun onItemClicked(item: News)
    }

