package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.AuthRepositoryImpl
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.vm.AuthViewModel
import com.praveen.gatherup.vm.AuthViewModelFactory
import com.praveen.gatherup.vm.UiState

@Composable
fun LoginFormScreen(navController: NavController) {
    val context = LocalContext.current
    val repo = remember { AuthRepositoryImpl(NetworkModule.authService) }
    val tokenStore = remember { TokenStore(context) }
    val factory = remember { AuthViewModelFactory(repo, tokenStore) }
    val viewModel: AuthViewModel = viewModel(factory = factory)

    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.loginState.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        Text(text = "Login", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is UiState.Loading -> {
                Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Signing in...")
                }
            }
            is UiState.Success -> {
                // successful login -> go to home
                LaunchedEffect(Unit) {
                    navController.navigate("home") {
                        popUpTo("login_form") { inclusive = true }
                    }
                }
            }
            is UiState.Error -> {
                val msg = (state as UiState.Error).message ?: "Login failed"
                Text(text = msg, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.login(mobile, password) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }
            }
            else -> {
                Button(onClick = { viewModel.login(mobile, password) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { navController.navigate("forgot_password") }) {
            Text("Forgot password?")
        }
    }
}
