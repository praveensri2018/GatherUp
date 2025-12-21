package com.praveen.gatherup.ui.components.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.praveen.gatherup.data.api.FeedItemDto

@Composable
fun PostCard(
    post: FeedItemDto,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit = {},
    onAuthorClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            /* ---------- HEADER ---------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
                )

                Spacer(Modifier.width(8.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        post.author.username,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        formatTime(post.created_at),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, null)
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ---------- CONTENT ---------- */
            post.content.title?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
            }

            post.content.body?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(10.dp))

            /* ---------- MEDIA (SAFE) ---------- */
            if (!post.media.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                Spacer(Modifier.height(10.dp))
            }

            /* ---------- ACTIONS ---------- */
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = onLikeClick) {
                    Icon(
                        if (post.viewer_state.liked)
                            Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Like"
                    )
                }
                Text(post.stats.likes.toString())

                Spacer(Modifier.width(16.dp))

                IconButton(onClick = onCommentClick) {
                    Icon(Icons.Default.Comment, null)
                }
                Text(post.stats.comments.toString())

                Spacer(Modifier.width(16.dp))

                IconButton(onClick = onShareClick) {
                    Icon(Icons.Default.Share, null)
                }

                Spacer(Modifier.weight(1f))

                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        if (post.viewer_state.bookmarked)
                            Icons.Default.Bookmark
                        else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }
            }
        }
    }
}

/* ---------- TIME FORMATTER ---------- */
private fun formatTime(raw: String): String {
    return try {
        raw.substring(0, 10) // simple safe fallback
    } catch (e: Exception) {
        raw
    }
}
