package com.example.authenticator.httpCommunication.repository

import com.example.authenticator.httpCommunication.Post
import com.example.authenticator.httpCommunication.api.RetroFitInstance

class Repository {

    suspend fun getPost(): Post {
        return RetroFitInstance.api.getPost()
    }
}