package com.praveen.gatherup.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.praveen.gatherup.R
import com.praveen.gatherup.security.TokenStore

@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1800) // splash duration

        val token = TokenStore(context).getAccessToken()

        if (!token.isNullOrBlank()) {
            // Already logged in → go to home
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            // Not logged in → go to login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progress Indicator
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(6.dp),
            )
        }

        // Bottom caption
        Text(
            text = "Bringing People Together",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        )
    }
}
