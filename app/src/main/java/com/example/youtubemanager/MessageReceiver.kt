package com.example.youtubemanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import android.app.NotificationChannel
import android.os.Build
import android.graphics.BitmapFactory
import android.app.Notification
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.youtubemanager.activity.MainActivity
import java.io.IOException
import java.net.URL

class MessageReceiver : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        showNotifications(remoteMessage)
        sendDataToActivity(remoteMessage)
    }

    private fun sendDataToActivity(remoteMessage: RemoteMessage){
        val notifyIntent = Intent(MainActivity.RECEIVE_FIREBASE_MESSAGE).apply{
            putExtra("title", "${remoteMessage.notification?.title}")
            putExtra("body", "${remoteMessage.notification?.body}")
        }
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(notifyIntent)
    }

    private fun showNotifications(remoteMessage: RemoteMessage) {
        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)
        val notification = remoteMessage.getNotification()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(notification?.getTitle())
            .setContentText(notification?.getBody())
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setContentInfo(notification?.getTitle())
            .setLargeIcon(icon)
//            .setColor(Color.BLUE)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.mipmap.ic_launcher_round)

        try {
            val picture_url = remoteMessage?.data?.get("picture_url")
            if (picture_url != null && "" != picture_url) {
                val url = URL(picture_url)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification?.getBody())
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "channel description"
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}