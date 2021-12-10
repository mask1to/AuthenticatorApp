package com.example.authenticator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.authenticator.httpCommunication.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: httpFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val httpModelFactory = httpFragmentModelFactory(repository)
        viewModel = ViewModelProvider(this, httpModelFactory).get(httpFragment::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            Log.d("Response", response.userId.toString())
            Log.d("Response", response.id.toString())
            Log.d("Response", response.title)
            Log.d("Response", response.body)

        })

    }

    override fun onSupportNavigateUp(): Boolean
    {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }
}