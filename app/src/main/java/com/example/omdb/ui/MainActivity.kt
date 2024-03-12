package com.example.omdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.omdb.R
import com.example.omdb.framework.navigation.Route
import com.example.omdb.framework.navigation.nav_graph.appNavGraph
import com.example.omdb.framework.navigation.nav_graph.authNavGraph
import com.example.omdb.framework.navigation.nav_graph.routerNavGraph
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Global
    private val TAG = MainActivity::class.java.simpleName
    private val connectionLiveData by lazy { ConnectionLiveData(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OMDbTheme {
                val isNightMode = isSystemInDarkTheme()
                val colorBackground = MaterialTheme.colorScheme.surface.toArgb()
                LaunchedEffect(key1 = colorBackground, key2 = isNightMode) {
                    window.statusBarColor = colorBackground
                    window.navigationBarColor = colorBackground
                    if (!isNightMode) {
                        val decorView = window.decorView
                        val controller = WindowInsetsControllerCompat(window, decorView)
                        controller.isAppearanceLightStatusBars = true
                        controller.isAppearanceLightNavigationBars = true
                    }
                }
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val isNetworkAvailable by connectionLiveData.observeAsState(initial = true)
                        AnimatedVisibility(visible = !isNetworkAvailable) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.error)
                                    .padding(all = 8.dp),
                                text = stringResource(id = R.string.label_no_network_connection),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.onError,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Route.Router.name,
                            route = Route.Root.name
                        ) {
                            routerNavGraph(navController = navController)
                            authNavGraph(navController = navController)
                            appNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}