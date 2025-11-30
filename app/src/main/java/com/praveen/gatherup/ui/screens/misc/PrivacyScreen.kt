package com.praveen.gatherup.ui.screens.misc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PrivacyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)  // ← FIXED
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = """
                This is a placeholder Privacy Policy. Replace with your real content.

                1. Data Collection...
                2. Cookies...
                3. 3rd Party Integrations...
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
