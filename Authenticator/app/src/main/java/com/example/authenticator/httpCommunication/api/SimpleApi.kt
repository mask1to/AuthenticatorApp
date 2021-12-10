package com.example.authenticator.httpCommunication.api

import com.example.authenticator.httpCommunication.Post
import retrofit2.http.GET

interface SimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Post
}