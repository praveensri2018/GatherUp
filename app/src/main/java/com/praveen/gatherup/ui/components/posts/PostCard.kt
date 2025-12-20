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

@Composable
fun PostCard(
    username: String,
    timeAgo: String,
    content: String,
    likes: Int,
    comments: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit = {},
    onBookmarkClick: (() -> Unit)? = null
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

                // Profile image placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(username, style = MaterialTheme.typography.labelLarge)
                    Text(timeAgo, style = MaterialTheme.typography.labelSmall)
                }

                IconButton(onClick = { /* more options later */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            /* ---------- TEXT CONTENT ---------- */
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* ---------- IMAGE PLACEHOLDER ---------- */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.height(10.dp))

            /* ---------- ACTIONS ---------- */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                /* LIKE */
                IconButton(onClick = onLikeClick) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Like"
                    )
                }
                Text(likes.toString())

                Spacer(modifier = Modifier.width(16.dp))

                /* COMMENT */
                IconButton(onClick = onCommentClick) {
                    Icon(
                        Icons.Default.Comment,
                        contentDescription = "Comment"
                    )
                }
                Text(comments.toString())

                Spacer(modifier = Modifier.width(16.dp))

                /* SHARE */
                IconButton(onClick = onShareClick) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                /* BOOKMARK (optional) */
                onBookmarkClick?.let {
                    IconButton(onClick = it) {
                        Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                }
            }
        }
    }
}
