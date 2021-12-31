package com.example.authenticator.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.authenticator.MainActivity
import com.example.authenticator.R
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

}