package com.praveen.gatherup.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.praveen.gatherup.security.TokenStore
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Home Screen")

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    TokenStore(context).clearTokens()

                    navController.navigate("login_form") {
                        // clear back stack up to home (including home)
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }) {
                    Text("Logout")
                }
            }
        }
    }
}
