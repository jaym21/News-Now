package com.example.newsnow.database

import android.content.Context
import androidx.room.*
import com.example.newsnow.apiModels.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsArticleDatabase: RoomDatabase(), NewsArticleDAO {
    //to get DAO
    abstract fun getNewsArticleDAO(): NewsArticleDAO

    companion object {
        @Volatile //volatile is added so other threads can see when this instance is changed
        private var instance: NewsArticleDAO? = null
        private val LOCK = Any() //used to synchronize instance

        //when database is initialized invoke function is called
        operator fun invoke(context: Context) = instance?: synchronized(LOCK) { //if instance is null then the we lock so no other thread can access db is already in use by a thread
            instance?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsArticleDatabase::class.java,
            "newsArticleDB"
        ).build()
    }
}