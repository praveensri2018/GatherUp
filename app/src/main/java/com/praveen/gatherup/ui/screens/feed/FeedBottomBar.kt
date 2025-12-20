package com.praveen.gatherup.ui.screens.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FeedBottomBar(navController: NavController) {
    NavigationBar {

        NavigationBarItem(
            selected = true,
            onClick = {
                navController.navigate("feed") {
                    popUpTo("feed") { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Feed") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("profile")
            },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
