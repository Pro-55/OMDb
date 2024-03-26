package com.example.analytics.analytic_engines

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseAnalyticEngine : AnalyticEngine {

    private lateinit var instnce: FirebaseAnalytics

    override fun setUp(context: Context) {
        instnce = FirebaseAnalytics.getInstance(context)
    }

    override fun logEvent(
        eventName: String,
        data: Bundle?
    ) {
        instnce.logEvent(eventName, data)
    }

    override fun logException(e: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }

    override fun logMessage(msg: String) {
        FirebaseCrashlytics.getInstance().log(msg)
    }
}