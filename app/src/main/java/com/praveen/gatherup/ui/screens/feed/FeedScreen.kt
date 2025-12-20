package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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

    /* ---------------- VIEW MODELS ---------------- */

    val feedVm = remember {
        FeedViewModel(
            FeedRepository(
                NetworkModule.feedService,
                tokenStore
            )
        )
    }

    val postActionVm = remember {
        PostActionViewModel(
            PostRepository(
                NetworkModule.postService,
                tokenStore
            )
        )
    }

    val socialVm = remember {
        SocialViewModel(
            SocialRepository(
                NetworkModule.socialService,
                tokenStore
            )
        )
    }

    /* ---------------- INITIAL LOAD ---------------- */

    LaunchedEffect(Unit) {
        feedVm.loadInitial()
    }

    val state by feedVm.state.collectAsState()
    val listState = rememberLazyListState()

    /* ---------------- UI ---------------- */

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
    ) { padding ->

        when (state) {

            /* ---------- LOADING ---------- */
            is FeedUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            /* ---------- ERROR ---------- */
            is FeedUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (state as FeedUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            /* ---------- SUCCESS ---------- */
            is FeedUiState.Success -> {
                val data = state as FeedUiState.Success

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    /* 🔥 SAFE LIST RENDERING (NO CRASH) */
                    items(
                        items = data.items,
                        key = { it.post_id }   // VERY IMPORTANT
                    ) { post ->

                        PostCard(
                            post = post,

                            onLikeClick = {
                                postActionVm.toggleLike(
                                    postId = post.post_id,
                                    liked = post.viewer_state.liked
                                )
                            },

                            onCommentClick = {
                                navController.navigate(
                                    "post_detail/${post.post_id}"
                                )
                            },

                            onBookmarkClick = {
                                socialVm.toggleBookmark(
                                    postId = post.post_id,
                                    bookmarked = post.viewer_state.bookmarked
                                )
                            }
                        )
                    }

                    /* ---------- PAGINATION LOADER ---------- */
                    if (data.loadingMore) {
                        item {
                            LaunchedEffect(Unit) {
                                feedVm.loadMore()
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}
