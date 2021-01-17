package com.example.newsnow.util


//it is a generic class to work with network responses to differentiate between successful and failed responses
// basically to handle success, error and loading state of network responses
//we can also add progress bar getting a response to show its loading
//sealed class is basically like an abstract class but we can set which classes can inherit from sealed class
sealed class Resource<T> (val data: T?= null, val responseMessage: String?= null){

    //success class means we get a response with data init
    class Success<T>(data: T): Resource<T>(data)

    //failed or error class means we did not get response with data so there will be an error message
    class Error<T>(responseMessage: String, data: T? = null): Resource<T>(data, responseMessage)

    //loading class for between responses
    class Loading<T> : Resource<T>()
}