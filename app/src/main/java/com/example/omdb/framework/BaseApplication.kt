package com.example.omdb.framework

import android.app.Application
import com.example.analytics.Analytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Analytics.setUp(this)
    }
}