package com.praveen.gatherup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.praveen.gatherup.navigation.AppNavGraph
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.ui.theme.GatherUpTheme
import com.praveen.gatherup.vm.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = TokenStore(this).getAccessToken()
        val startDest = if (!token.isNullOrBlank()) "home" else "login"

        setContent {
            GatherUpTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    startDestination = startDest
                )
            }
        }
    }
}
