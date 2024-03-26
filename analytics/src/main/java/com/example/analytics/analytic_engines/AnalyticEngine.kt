package com.example.analytics.analytic_engines

import android.content.Context
import android.os.Bundle

interface AnalyticEngine {
    fun setUp(context: Context) {}
    fun logEvent(eventName: String, data: Bundle?)
    fun logException(e: Throwable)
    fun logMessage(msg: String)
    fun tearDown() {}
}