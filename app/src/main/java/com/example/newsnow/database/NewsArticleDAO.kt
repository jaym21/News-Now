package com.example.newsnow.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsnow.apiModels.Article

@Dao
interface NewsArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM NewsArticles")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}