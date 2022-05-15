package com.example.omdb.util

object Constants {
    const val OMDB_SHARED_PREFS = "omdb_shared_preferences"

    const val KEY_SIGN_UP_STATUS = "sign_up_status"
    const val KEY_USER_ID = "user_id"
    const val KEY_FCM_TOKEN = "fcm_token"

    const val KEY_NOTIFICATION_ID = "notification_id"
    const val KEY_NOTIFICATION_TITLE = "title"
    const val KEY_NOTIFICATION_BODY = "body"
    const val KEY_NOTIFICATION_CHANNEL_ID = "channel_id"
    const val KEY_NOTIFICATION_COLOR = "color"

    const val NOTIFICATION_INTENT_FILTER = "FIREBASE_MESSAGING_EVENT"

    const val REQUEST_GOOGLE_SIGN_IN = 0

    private const val EMOJI_MONOCLE_FACE = "\uD83E\uDDD0"
    private const val EMOJI_TURTLE = "\uD83D\uDC22"
    private const val EMOJI_EXPLODING_HEAD = "\uD83E\uDD2F"
    private const val EMOJI_SPIRAL_EYES = "\uD83D\uDE35\u200D\uD83D\uDCAB"
    private const val THINKING_EMOJI = "\uD83E\uDD14"
    const val RISING_SUN_EMOJI = "\uD83C\uDF04"
    const val SUN_EMOJI = "\u26C5"
    const val SETTING_SUN_EMOJI = "\uD83C\uDF06"
    const val CRESCENT_MOON_EMOJI = "\uD83C\uDF19"
    const val REQUEST_FAILED_MESSAGE = "Something went wrong... $THINKING_EMOJI"
    const val ERROR_MESSAGE_INVALID_PATH = "We couldn't find the data... $EMOJI_SPIRAL_EYES"
    const val ERROR_MESSAGE_INVALID_REQUEST = "Failed to process the request... $EMOJI_MONOCLE_FACE"
    const val ERROR_MESSAGE_SERVER_EXCEPTION =
        "Servers are down! Please try again later... $EMOJI_TURTLE"
    const val ERROR_MESSAGE_UNKNOWN = "Something went wrong... $EMOJI_EXPLODING_HEAD"
    const val NOT_AVAILABLE = "N/A"
}