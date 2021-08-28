package com.example.omdb.util

object Constants {
    const val OMDB_SHARED_PREFS = "omdb_shared_preferences"

    const val KEY_SIGN_UP_STATUS = "sign_up_status"
    const val KEY_USER_ID = "user_id"
    const val KEY_FCM_TOKEN = "fcm_token"

    const val KEY_NOTIFICATION_ID = "notification_id"
    const val KEY_NOTIFICATION_TITLE = "title"
    const val KEY_NOTIFICATION_BODY = "body"
    const val KEY_NOTIFICATION_CHANEL_ID = "channel_id"
    const val KEY_NOTIFICATION_COLOR = "color"

    const val NOTIFICATION_INTENT_FILTER = "FIREBASE_MESSAGING_EVENT"

    const val REQUEST_GOOGLE_SIGN_IN = 0

    const val BULLET = "\u2022"
    const val GHOST_EMOJI = "\uD83D\uDC7B"
    const val THINKING_EMOJI = "\uD83E\uDD14"
    const val RISING_SUN_EMOJI = "\uD83C\uDF04"
    const val SUN_EMOJI = "\u26C5"
    const val SETTING_SUN_EMOJI = "\uD83C\uDF06"
    const val CRESCENT_MOON_EMOJI = "\uD83C\uDF19"
    const val NETWORK_ERROR_MESSAGE = "Oops, your connection seems off... $GHOST_EMOJI"
    const val REQUEST_FAILED_MESSAGE = "Something went wrong... $THINKING_EMOJI"
    const val NOT_AVAILABLE = "N/A"
}