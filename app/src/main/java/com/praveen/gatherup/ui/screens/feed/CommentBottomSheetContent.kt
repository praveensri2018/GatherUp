package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.CommentRepository
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.vm.CommentViewModel

@Composable
fun CommentBottomSheetContent(
    postId: String,
    onClose: () -> Unit
) {

    val context = LocalContext.current

    val vm = remember {
        CommentViewModel(
            CommentRepository(
                NetworkModule.commentService,
                TokenStore(context)
            ),
            postId
        )
    }

    val state by vm.state.collectAsState()
    var commentText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.9f) // 🔥 Instagram feel
            .padding(12.dp)
    ) {

        Text(
            text = "Comments",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.comments) { comment ->
                Text(
                    text = comment.body,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {

            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Add a comment...") }
            )

            IconButton(
                onClick = {
                    vm.addComment(commentText)
                    commentText = ""
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}
