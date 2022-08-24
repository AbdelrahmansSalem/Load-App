package com.udacity

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        custom_button.setOnClickListener {

            var chechedId = radioGroup.checkedRadioButtonId
            var radioButton:RadioButton?=findViewById(chechedId)
            when (chechedId) {
                R.id.retrofit_button -> {
                    download(Constants.RETROFIT)
                    var  notificationManager=ContextCompat.getSystemService(applicationContext,NotificationManager::class.java)as NotificationManager
                    notificationManager.sendNotification(getString(R.string.Notification_Message),applicationContext,radioButton?.text.toString())
                    Toast.makeText(this, "retrofit", Toast.LENGTH_SHORT).show()}

                R.id.glile_button -> {
                    download(Constants.GLIDE)
                    Toast.makeText(this, "glide", Toast.LENGTH_SHORT).show()
                    var  notificationManager=ContextCompat.getSystemService(applicationContext,NotificationManager::class.java)as NotificationManager
                    notificationManager.sendNotification(getString(R.string.Notification_Message),applicationContext,radioButton?.text.toString())
                    }

                R.id.udacity_button -> {
                    download(Constants.UDACITY)
                    var  notificationManager=ContextCompat.getSystemService(applicationContext,NotificationManager::class.java)as NotificationManager
                    notificationManager.sendNotification(getString(R.string.Notification_Message),applicationContext,radioButton?.text.toString())
                    Toast.makeText(this, "udacity", Toast.LENGTH_SHORT).show()}

                else->Toast.makeText(this, "Select Link First", Toast.LENGTH_SHORT).show()
            }
        }
        createChannel(applicationContext,getString(R.string.Channel_Id),getString(R.string.Channel_Name))
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    private fun download(url:String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

}
fun createChannel(context: Context,channelId: String, channelName: String){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        var notificationChannel= NotificationChannel(channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH)
            .apply {
                lightColor= Color.RED
                description="Downloading"
                enableLights(true)
                enableVibration(true)
            }
        var notificationManager= getSystemService(context,NotificationManager::class.java)
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}

object Constants {
    const val GLIDE = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
    const val UDACITY = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
    const val RETROFIT = "https://github.com/square/retrofit/archive/heads/master.zip"
}