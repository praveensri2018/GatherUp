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
fun TermsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)   // ← FIXED: using padding instead of contentPadding
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Terms of Service",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = """
                This is a placeholder Terms of Service.

                Replace with your real content or load a webpage.

                1. Use of service...
                2. User obligations...
                3. Liability...
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
