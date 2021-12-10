package com.example.authenticator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authenticator.httpCommunication.Post
import com.example.authenticator.httpCommunication.repository.Repository
import kotlinx.coroutines.launch

class httpFragment(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Post> = MutableLiveData()

    fun getPost(){
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }

}