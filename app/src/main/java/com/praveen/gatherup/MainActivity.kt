package com.praveen.gatherup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.praveen.gatherup.data.AuthStore
import com.praveen.gatherup.data.repository.AuthRepository
import com.praveen.gatherup.ui.navigation.AppNavGraph
import com.praveen.gatherup.ui.theme.GatherUpTheme
import com.praveen.gatherup.viewmodel.AuthViewModel
import com.praveen.gatherup.viewmodel.AuthViewModelFactory

/**
 * MainActivity — decides start destination:
 *  - if token exists -> startDestination = "home"
 *  - otherwise -> "login"
 *
 * It creates the AuthViewModel using a simple AuthRepository.
 */
class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create repo + viewmodel (replace with DI if you use one)
        val repo = AuthRepository()
        authViewModel = ViewModelProvider(this, AuthViewModelFactory(repo))[AuthViewModel::class.java]

        // Read stored token (simple SharedPreferences)
        val token = AuthStore(this).getToken()

        val startDest = if (!token.isNullOrBlank()) "home" else "login"

        setContent {
            GatherUpTheme {
                val navController = rememberNavController()
                // Pass startDestination so NavHost starts at login/home correctly
                AppNavGraph(navController = navController, authViewModel = authViewModel, startDestination = startDest)
            }
        }
    }
}