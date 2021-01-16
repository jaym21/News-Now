package com.example.newsnow.database

import androidx.room.TypeConverter
import com.example.newsnow.apiModels.Source


//for database to work with the source class from api and convert it to string
//basically directing db how to handle the source class
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        //whenever room encounters source from api it will get name string from it
        return source.name
    }

    //to convert a string into source class
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}