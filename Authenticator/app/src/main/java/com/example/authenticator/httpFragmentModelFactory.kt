package com.example.authenticator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authenticator.httpCommunication.repository.Repository

class httpFragmentModelFactory(
    private val repository: Repository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return httpFragment(repository) as T
    }

}