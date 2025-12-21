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

@OptIn(ExperimentalMaterial3Api::class)
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

    /* ---------------- COMMENT SHEET STATE ---------------- */

    var showComments by remember { mutableStateOf(false) }
    var selectedPostId by remember { mutableStateOf<String?>(null) }

    /* ---------------- INITIAL LOAD ---------------- */

    LaunchedEffect(Unit) {
        feedVm.loadInitial()
    }

    val state by feedVm.state.collectAsState()
    val listState = rememberLazyListState()

    /* ---------------- UI ---------------- */

    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {

            is FeedUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is FeedUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (state as FeedUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is FeedUiState.Success -> {
                val data = state as FeedUiState.Success

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(
                        items = data.items,
                        key = { it.post_id }
                    ) { post ->

                        PostCard(
                            post = post,

                            onAuthorClick = {
                                navController.navigate("profile/user/${post.author.id}")
                            },

                            onLikeClick = {
                                postActionVm.toggleLike(
                                    postId = post.post_id,
                                    liked = post.viewer_state.liked
                                ) {
                                    feedVm.updateLikeState(post.post_id)
                                }
                            },

                            onCommentClick = {
                                selectedPostId = post.post_id
                                showComments = true
                            },

                            onBookmarkClick = {
                                socialVm.toggleBookmark(
                                    postId = post.post_id,
                                    bookmarked = post.viewer_state.bookmarked
                                )
                            }
                        )

                    }

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

        /* ---------- FAB ---------- */

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                navController.navigate("create_post")
            }
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Create Post")
        }
    }

    /* ---------- COMMENT BOTTOM SHEET ---------- */

    if (showComments && selectedPostId != null) {

        ModalBottomSheet(
            onDismissRequest = {
                showComments = false
                selectedPostId = null
            },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
        ) {
            CommentBottomSheetContent(
                postId = selectedPostId!!,
                onClose = {
                    showComments = false
                    selectedPostId = null
                }
            )
        }
    }
}
