package com.example.omdb

import android.util.Log
import androidx.work.Configuration
import com.example.omdb.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication(), Configuration.Provider {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build()

}