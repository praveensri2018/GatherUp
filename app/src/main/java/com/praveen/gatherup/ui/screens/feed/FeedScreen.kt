package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.ui.components.posts.PostCard

@Composable
fun FeedScreen(navController: NavController) {

    Scaffold(
        topBar = { FeedTopBar() },
        bottomBar = { FeedBottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // later: navigate to PostComposerScreen
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Create Post")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(dummyFeed) {
                PostCard(
                    username = "joshua_l",
                    timeAgo = "2h ago",
                    content = "Just wrapped up an amazing weekend hiking trip in the mountains. " +
                            "The views were absolutely breathtaking!",
                    likes = 88,
                    comments = 12
                )
            }
        }
    }
}

/* ---------------- TOP BAR ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.People, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("GatherUp")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }

            BadgedBox(
                badge = { Badge { Text("2") } }
            ) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }
        }
    )
}

/* ---------------- BOTTOM BAR ---------------- */

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

        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Search, null) },
            label = { Text("Search") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Event, null) },
            label = { Text("Events") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}

/* ---------------- DUMMY DATA ---------------- */

private val dummyFeed = listOf(1, 2, 3, 4, 5)
