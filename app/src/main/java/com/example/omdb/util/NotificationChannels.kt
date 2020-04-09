package com.example.omdb.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Resources
import android.os.Build
import com.example.omdb.R

object NotificationChannels {

    fun create(manager: NotificationManager, resources: Resources) {

        val channelData = arrayListOf(
            Pair(
                resources.getString(R.string.default_notification_channel_id),
                resources.getString(R.string.channel_notification_default)
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = channelData.map {
                NotificationChannel(it.first, it.second, NotificationManager.IMPORTANCE_HIGH)
                    .apply { enableVibration(true) }
            }
            manager.createNotificationChannels(channels)
        }
    }

}