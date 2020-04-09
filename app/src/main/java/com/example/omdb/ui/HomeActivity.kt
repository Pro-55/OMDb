package com.example.omdb.ui

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.omdb.R
import com.example.omdb.databinding.ActivityHomeBinding
import com.example.omdb.util.NotificationChannels
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }

    //Global
    private lateinit var binding: ActivityHomeBinding
    private val manager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        NotificationChannels.create(manager, resources)
    }
}
