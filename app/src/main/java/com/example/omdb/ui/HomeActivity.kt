package com.example.omdb.ui

import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.example.omdb.R
import com.example.omdb.databinding.ActivityHomeBinding
import com.example.omdb.util.ConnectionLiveData
import com.example.omdb.util.NotificationChannels
import com.example.omdb.util.extensions.goneWithScaleFade
import com.example.omdb.util.extensions.visibleWithScaleFade
import com.example.omdb.util.transition.Scale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    //Global
    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var binding: ActivityHomeBinding
    private val manager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val colorBackground = resources.getColor(R.color.color_background, null)
            window.statusBarColor = colorBackground
            window.navigationBarColor = colorBackground
            val isNightMode =
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> true
                    else -> false
                }
            if (!isNightMode) {
                val decorView = window.decorView
                val controller = WindowInsetsControllerCompat(window, decorView)
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        ConnectionLiveData(this).observe(this, { isNetworkAvailable ->
            binding.txtNetworkStatus.apply {
                val parent = this.parent as ViewGroup
                val direction = Scale.Direction.DOWN
                if (!isNetworkAvailable)
                    visibleWithScaleFade(parent = parent, direction = direction)
                else
                    goneWithScaleFade(parent = parent, direction = direction)
            }
        })

        NotificationChannels.create(manager, resources)
    }
}
