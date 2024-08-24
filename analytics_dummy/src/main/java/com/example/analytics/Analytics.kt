package com.example.analytics

import android.content.Context
import android.os.Bundle

object Analytics {

    fun setUp(context: Context) {}

    fun logEvent(eventName: String) {}

    fun logEvent(eventName: String, data: Bundle?) {}

    fun logException(e: Throwable) {}

    fun logMessage(msg: String) {}

    fun tearDown() {}
}