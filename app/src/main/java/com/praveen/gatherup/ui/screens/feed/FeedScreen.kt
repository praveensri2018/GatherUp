package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.*
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.ui.components.posts.PostCard
import com.praveen.gatherup.vm.*

@Composable
fun FeedScreen(navController: NavController) {

    val context = LocalContext.current
    val tokenStore = remember { TokenStore(context) }

    /* ---------- FEED VM ---------- */
    val feedVm = remember {
        FeedViewModel(
            FeedRepository(
                NetworkModule.feedService,
                tokenStore
            )
        )
    }

    /* ---------- POST ACTION VM (Like) ---------- */
    val postActionVm = remember {
        PostActionViewModel(
            PostRepository(
                NetworkModule.postService,
                tokenStore
            )
        )
    }

    /* ---------- SOCIAL VM (Bookmark / Follow) ---------- */
    val socialVm = remember {
        SocialViewModel(
            SocialRepository(
                NetworkModule.socialService,
                tokenStore
            )
        )
    }

    /* ---------- Load Feed Once ---------- */
    LaunchedEffect(Unit) {
        feedVm.loadFeed()
    }

    val state by feedVm.state.collectAsState()

    Scaffold(
        topBar = { FeedTopBar() },
        bottomBar = { FeedBottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_post") }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Create Post")
            }
        }
    ) { paddingValues ->

        when (state) {

            /* ---------- LOADING ---------- */
            is FeedUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            /* ---------- ERROR ---------- */
            is FeedUiState.Error -> {
                val msg = (state as FeedUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            /* ---------- SUCCESS ---------- */
            is FeedUiState.Success -> {
                val posts = (state as FeedUiState.Success).posts

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(posts) { post ->

                        PostCard(
                            username = post.author_id.take(6),
                            timeAgo = post.created_at,
                            content = buildString {
                                if (!post.title.isNullOrBlank()) {
                                    append(post.title)
                                    append("\n\n")
                                }
                                append(post.body ?: "")
                            },
                            likes = 0,
                            comments = 0,

                            /* LIKE */
                            onLikeClick = {
                                postActionVm.toggleLike(
                                    postId = post.id,
                                    liked = false
                                )
                            },

                            /* COMMENT */
                            onCommentClick = {
                                navController.navigate("post_detail/${post.id}")
                            },

                            /* SHARE (placeholder) */
                            onShareClick = {
                                // TODO: Android share intent
                            },

                            /* BOOKMARK */
                            onBookmarkClick = {
                                socialVm.bookmark(post.id)
                            }
                        )
                    }
                }
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
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
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
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
