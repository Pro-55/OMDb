package com.example.omdb.util

object Constants {
    const val OMDB_SHARED_PREFS = "omdb_shared_preferences"

    const val KEY_FCM_TOKEN = "fcm_token"

    const val KEY_NOTIFICATION_ID = "notification_id"
    const val KEY_NOTIFICATION_TITLE = "title"
    const val KEY_NOTIFICATION_BODY = "body"
    const val KEY_NOTIFICATION_CHANEL_ID = "channel_id"
    const val KEY_NOTIFICATION_COLOR = "color"

    const val NOTIFICATION_INTENT_FILTER = "FIREBASE_MESSAGING_EVENT"

    const val BULLET = "\u2022"
    const val GHOST_EMOJI = "\uD83D\uDC7B"
    const val THINKING_EMOJI = "\uD83E\uDD14"
    const val NETWORK_ERROR_MESSAGE = "Oops, your connection seems off... $GHOST_EMOJI"
    const val REQUEST_FAILED_MESSAGE = "Something went wrong... $THINKING_EMOJI"
}