package com.praveen.gatherup.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.model.MeDto
import com.praveen.gatherup.data.repository.UserRepository
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.vm.*
import androidx.navigation.NavController
@Composable
fun ProfileScreen(
    navController: NavController,
    isMe: Boolean = true
) {
    val context = LocalContext.current
    val tokenStore = remember { TokenStore(context) }

    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            UserRepository(
                NetworkModule.userService,
                tokenStore
            )
        )
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMyProfile()
    }

    when (val uiState = state) {

        is ProfileUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is ProfileUiState.Success -> {
            ProfileContent(
                me = uiState.me,
                isMe = isMe,
                onLogout = {
                    // ✅ Clear tokens & cached profile
                    tokenStore.clearTokens()

                    // ✅ Navigate to login & clear back stack
                    navController.navigate("login_form") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileContent(
    me: MeDto,
    isMe: Boolean,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E1621))
    ) {

        /* ---------- HEADER ---------- */
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(Modifier.width(16.dp))

            Column {

                Text(
                    text = me.username ?: "User",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                me.email?.let {
                    Text(it, color = Color.Gray)
                }

                me.mobile_number?.let {
                    Text(it, color = Color.Gray)
                }

                Spacer(Modifier.height(12.dp))

                if (isMe) {
                    Row {
                        Button(
                            onClick = { /* navigate edit profile */ },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Edit Profile")
                        }

                        Spacer(Modifier.width(8.dp))

                        OutlinedButton(
                            onClick = onLogout,
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Logout")
                        }
                    }
                }
            }
        }

        Divider(color = Color.DarkGray)

        /* ---------- BASIC INFO ---------- */
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                ProfileInfoRow("User ID", me.id)
                ProfileInfoRow("Joined", me.created_at)
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            label,
            color = Color.Gray,
            fontSize = MaterialTheme.typography.labelSmall.fontSize
        )
        Text(value, fontWeight = FontWeight.Medium)
    }
}
