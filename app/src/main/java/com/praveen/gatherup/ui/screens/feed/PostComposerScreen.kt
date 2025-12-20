package com.praveen.gatherup.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.PostRepository
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.vm.CreatePostState
import com.praveen.gatherup.vm.CreatePostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostComposerScreen(navController: NavController) {

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Post") },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.createPost(title, body)
                        }
                    ) {
                        Text("Post")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

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

            when (state) {
                is CreatePostState.Loading -> CircularProgressIndicator()
                is CreatePostState.Error ->
                    Text(
                        (state as CreatePostState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                is CreatePostState.Success -> {
                    // go back to feed
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }
                else -> {}
            }
        }
    }
}
