package com.example.authenticator.database

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import com.example.authenticator.R
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ConnectionHelper
{
    private var connection: Connection? = null
    
    private val host = "localhost"
    private val database = "postgres"
    private val port = 5432
    private val user = "postgres"
    private val pass = "admin"
    private var url = "jdbc:postgresql://%s:%d/%s"
    private var status = false

    init {
        url = String.format(url, host, port, database)
        println("The URl is:  $url")
        connect()
        //this.disconnect();
        println("connection status:$status")
    }

    private fun connect() {
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                status = true
                println("connected:$status")
            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun getExtraConnection(): Connection? {
        var c: Connection? = null
        try {
            Class.forName("org.postgresql.Driver")
            c = DriverManager.getConnection(url, user, pass)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return c
    }

}