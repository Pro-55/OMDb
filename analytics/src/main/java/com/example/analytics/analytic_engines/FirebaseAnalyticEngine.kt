package com.example.analytics.analytic_engines

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseAnalyticEngine : AnalyticEngine {

    private lateinit var instance: FirebaseAnalytics

    override fun setUp(context: Context) {
        instance = FirebaseAnalytics.getInstance(context)
    }

    override fun logEvent(
        eventName: String,
        data: Bundle?
    ) {
        instance.logEvent(eventName, data)
    }

    override fun logException(e: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }

    override fun logMessage(msg: String) {
        FirebaseCrashlytics.getInstance().log(msg)
    }
}