package com.example.analytics

import android.content.Context
import android.os.Bundle
import com.example.analytics.analytic_engines.AnalyticEngine
import com.example.analytics.analytic_engines.FirebaseAnalyticEngine

object Analytics : AnalyticEngine {

    private lateinit var firebaseAnalytic: FirebaseAnalyticEngine
    private val engines = mutableListOf<AnalyticEngine>()

    override fun setUp(context: Context) {
        firebaseAnalytic = FirebaseAnalyticEngine()
        firebaseAnalytic.setUp(context)
        engines.add(firebaseAnalytic)
    }

    fun logEvent(eventName: String) {
        logEvent(
            eventName = eventName,
            data = null
        )
    }

    override fun logEvent(
        eventName: String,
        data: Bundle?
    ) {
        engines.forEach {
            it.logEvent(
                eventName = eventName,
                data = data
            )
        }
    }

    override fun logException(e: Throwable) {
        engines.forEach { it.logException(e = e) }
    }

    override fun logMessage(msg: String) {
        engines.forEach { it.logMessage(msg = msg) }
    }

    override fun tearDown() {
        engines.forEach { it.tearDown() }
    }
}