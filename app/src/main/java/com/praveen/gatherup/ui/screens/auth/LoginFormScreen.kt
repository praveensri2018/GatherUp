package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.praveen.gatherup.R
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.repository.AuthRepositoryImpl
import com.praveen.gatherup.security.TokenStore
import com.praveen.gatherup.ui.theme.GatherUpTheme
import com.praveen.gatherup.ui.theme.Shapes
import com.praveen.gatherup.vm.AuthViewModel
import com.praveen.gatherup.vm.AuthViewModelFactory
import com.praveen.gatherup.vm.UiState

/**
 * Runtime screen: wires ViewModel + navigation behaviour.
 */
@Composable
fun LoginFormScreen(navController: NavController) {
    val context = LocalContext.current
    val repo = remember { AuthRepositoryImpl(NetworkModule.authService) }
    val tokenStore = remember { TokenStore(context) }
    val factory = remember { AuthViewModelFactory(repo, tokenStore) }
    val viewModel: AuthViewModel = viewModel(factory = factory)

    val state by viewModel.loginState.collectAsState()

    // on success navigate to home
    if (state is UiState.Success) {
        LaunchedEffect(state) {
            navController.navigate("home") {
                popUpTo("login_form") { inclusive = true }
            }
        }
    }

    LoginFormContent(
        state = state,
        onLogin = { mobile, password -> viewModel.login(mobile, password) },
        onForgot = { navController.navigate("forgot_password") },
        onRegister = { navController.navigate("register") }
    )
}

/**
 * Pure UI (previewable). Works with your Material3 theme and Compose 1.5.3.
 */
@Composable
fun LoginFormContent(
    state: UiState<Any> = UiState.Idle,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onForgot: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Logo circle
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(colors.primary),
            contentAlignment = Alignment.Center
        ) {
            // Use your splash/logo asset. If different, change R.drawable.ic_splash_logo
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "App logo",
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Welcome Back!",
            style = typography.displayLarge,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Log in to your account to continue.",
            style = typography.bodyLarge,
            color = colors.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Mobile field label + input
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            placeholder = { Text("Enter your mobile number") },
            leadingIcon = { androidx.compose.material3.Icon(Icons.Default.Phone, contentDescription = null, tint = colors.onBackground.copy(alpha = 0.6f)) },
            singleLine = true,
            shape = Shapes.large,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.outline,
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password label + forgot link

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            singleLine = true,
            shape = Shapes.large,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = colors.onBackground.copy(alpha = 0.7f)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.outline,
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action button (reacts to UiState)
        when (state) {
            is UiState.Loading -> {
                Button(
                    onClick = { /* no-op while loading */ },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = colors.onPrimary, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Signing in...", color = colors.onPrimary, style = typography.labelLarge)
                }
            }
            else -> {
                Button(
                    onClick = { onLogin(mobile.trim(), password) },
                    enabled = mobile.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                ) {
                    Text(text = "Login", style = typography.labelLarge, color = colors.onPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onForgot) {
                Text(
                    text = "Forgot Password?",
                    color = colors.primary,
                    style = typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Don't have an account?",
                style = typography.bodyLarge,
                color = colors.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextButton(onClick = onRegister) {
                Text(
                    text = "Register",
                    color = colors.primary,
                    style = typography.bodyLarge
                )
            }
        }
    }
}

/* ---------------- Previews ---------------- */

@Preview(showBackground = true)
@Composable
fun LoginFormPreviewLight() {
    GatherUpTheme(darkTheme = false) {
        Surface {
            LoginFormContent(state = UiState.Idle)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreviewDark() {
    GatherUpTheme(darkTheme = true) {
        Surface {
            LoginFormContent(state = UiState.Idle)
        }
    }
}
