package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.PostRepository
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.vm.CreatePostViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.praveen.gatherup.vm.*

@Composable
fun PostComposerContent(navController: NavController) {

    val context = LocalContext.current

    val viewModel = remember {
        CreatePostViewModel(
            PostRepository(
                NetworkModule.postService,
                TokenStore(context)
            )
        )
    }

    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Create Post",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("What's on your mind?") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.createPost(title, body)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Post")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CreatePostState.Loading ->
                CircularProgressIndicator()

            is CreatePostState.Error ->
                Text(
                    (state as CreatePostState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )

            is CreatePostState.Success -> {
                Text("Posted successfully ✅")
                // optional: move back to feed
                // pagerState.animateScrollToPage(0)
            }

            else -> {}
        }
    }
}
