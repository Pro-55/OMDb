package com.example.omdb.data.fcm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavDeepLinkBuilder
import com.example.omdb.R
import com.example.omdb.ui.HomeActivity
import com.example.omdb.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class FirebaseMessagingServiceClass : FirebaseMessagingService() {

    // Global
    private val TAG = FirebaseMessagingServiceClass::class.java.simpleName

    /**
     * Called when message is received.
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        val requestId =
            Integer.parseInt(SimpleDateFormat("ddHHmssS", Locale.ENGLISH).format(Date()))

        val data = remoteMessage.data

//        showDeepLinkNotification(requestId, data)

//        showSystemNotification(requestId, data)

//        broadcastNotificationData(requestId, data)
    }

    private fun showDeepLinkNotification(requestId: Int, data: MutableMap<String, String>) {

        // Check if message contains a data payload.
        data.isNotEmpty().let {

            val args = Bundle()
            data.keys.forEach { key -> args.putString(key, data[key]) }

            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.navigation_graph)
                .setDestination(R.id.detailsFragment)
                .setArguments(args)
                .createPendingIntent()

            buildNotification(requestId, data, pendingIntent)
        }
    }

    private fun showSystemNotification(requestId: Int, data: MutableMap<String, String>) {

        // Check if message contains a data payload.
        data.isNotEmpty().let {

            val notificationIntent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            data.keys.forEach { key -> notificationIntent.putExtra(key, data[key]) }

            val pendingIntent = PendingIntent.getActivity(
                this, requestId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            buildNotification(requestId, data, pendingIntent)
        }
    }

    private fun broadcastNotificationData(requestId: Int, data: MutableMap<String, String>) {

        // Check if message contains a data payload.
        if (data.isNotEmpty()) {
            val notificationIntent = Intent(Constants.NOTIFICATION_INTENT_FILTER)
            notificationIntent.putExtra(Constants.KEY_NOTIFICATION_ID, requestId)
            data.keys.forEach { key -> notificationIntent.putExtra(key, data[key]) }

            val localBroadcastManager = LocalBroadcastManager.getInstance(this)
            localBroadcastManager.sendBroadcast(notificationIntent)
        }
    }

    private fun buildNotification(
        requestId: Int,
        data: MutableMap<String, String>,
        pendingIntent: PendingIntent
    ) {
        val builder = NotificationCompat.Builder(
            this,
            data[Constants.KEY_NOTIFICATION_CHANEL_ID]
                ?: resources.getString(R.string.default_notification_channel_id)
        )

        builder.setSmallIcon(R.drawable.ic_notification_badge)
        val color = data[Constants.KEY_NOTIFICATION_COLOR]
        builder.color = if (color != null) Color.parseColor(color)
        else ResourcesCompat.getColor(resources, R.color.colorAccent, null)

        builder.apply {
            setContentTitle(data[Constants.KEY_NOTIFICATION_TITLE])
            setContentText(data[Constants.KEY_NOTIFICATION_BODY])
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        with(NotificationManagerCompat.from(this)) { notify(requestId, builder.build()) }
    }

    /**
     * Called if InstanceID token is updated.
     */
    override fun onNewToken(token: String) {
        val sp = getSharedPreferences()

        //Save new token
        sp.edit().putString(Constants.KEY_FCM_TOKEN, token).apply()

    }

    private fun getSharedPreferences(): SharedPreferences =
        application.getSharedPreferences(Constants.OMDB_SHARED_PREFS, Context.MODE_PRIVATE)

}