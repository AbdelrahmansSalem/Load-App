package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

private val NOTIFICATIONID=0

fun NotificationManager.sendNotification(text:String,applicationContext: Context,filename: String){

    var intent=Intent(applicationContext,DetailActivity::class.java)
    intent.putExtra("file_name",filename)
    var pendingIntent=PendingIntent.getActivity(applicationContext,
        NOTIFICATIONID,
    intent,PendingIntent.FLAG_UPDATE_CURRENT)

    var builder=NotificationCompat.Builder(applicationContext
        ,applicationContext.getString(R.string.Channel_Id))

        .setContentTitle(applicationContext.getText(R.string.Notification_Title))
        .setSmallIcon(R.drawable.download_icon)
        .setContentText(text)
        .addAction(R.drawable.download_icon,applicationContext.getText(R.string.showDetails),pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATIONID,builder.build())
}


