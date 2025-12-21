package com.praveen.gatherup.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.ui.screens.feed.FeedScreen
import com.praveen.gatherup.ui.screens.feed.FeedTopBar
import com.praveen.gatherup.ui.screens.feed.PostComposerContent
import com.praveen.gatherup.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 } // Feed, Search, Events, Profile
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (pagerState.currentPage == 0) {
                FeedTopBar()
            }
        },
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(0) }
                    },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Feed") }
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(1) }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Post"
                        )
                    },
                    label = { Text("Post") }
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 2,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(2) }
                    },
                    icon = { Icon(Icons.Default.Event, null) },
                    label = { Text("Events") }
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 3,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(3) }
                    },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        },
        floatingActionButton = {
            if (pagerState.currentPage == 0) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("create_post")
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Create Post")
                }
            }
        }
    ) { padding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { page ->

            when (page) {
                0 -> FeedScreen(navController)
                1 -> PostComposerContent(navController)
                2 -> Box(modifier = Modifier.fillMaxSize()) { /* Events later */ }
                3 -> ProfileScreen()
            }
        }
    }
}
