package com.example.newsnow.util

//it is a generic class to work with network responses to differentiate between successful and failed responses
//we can also add progress bar getting a response to show its loading
//sealed class is basically like an abstract class but we can set which classes can inherit from sealed class
sealed class Resource<T> (val data: T?= null, val responseMessage: String?= null){

}