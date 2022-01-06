package com.example.authenticator.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.authenticator.MainActivity
import com.example.authenticator.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channel_id = "notification_channel"
const val channel_name = "com.example.authenticator.firebase"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // generate the notification

    // attach the notification created with the custom layout

    // show the notification

    override fun onMessageReceived(remoteMessage: RemoteMessage)
    {
       if(remoteMessage.notification != null)
       {
           generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
       }
    }

    fun getRemoteView(title : String, message: String) : RemoteViews
    {
        val remoteView = RemoteViews("com.example.authenticator", R.layout.notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.description, message)
        remoteView.setImageViewResource(R.id.appIcon, R.drawable.icon)

        return remoteView
    }

    fun generateNotification(title : String, message : String)
    {
        retrieveAndStoreToken() //generovanie tokenu
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // channel id & name
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationChannel = NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    fun retrieveAndStoreToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // ulozi token to firebaseDatabase, tam sa potom da token pozriet
            FirebaseDatabase.getInstance()
                .getReference("tokens")
                .setValue(token)

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(ContentValues.TAG, msg)

        })
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(ContentValues.TAG, "sendRegistrationTokenToServer($token)")
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    override fun onNewToken(token: String) {
        Log.d(ContentValues.TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

}