package com.example.omdb.ui

import android.os.Bundle
import com.example.omdb.R
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
