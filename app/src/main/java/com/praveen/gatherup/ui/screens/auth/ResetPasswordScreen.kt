package com.praveen.gatherup.ui.screens.auth

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.model.ResetPasswordRequest
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(navController: NavController, mobile: String) {
    var newPass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var info by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val authService = remember { NetworkModule.authService }

    val canSubmit = newPass.isNotBlank() &&
            confirm.isNotBlank() &&
            newPass == confirm

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newPass,
                onValueChange = { newPass = it },
                label = { Text("New Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (info != null) {
                Text(
                    text = info!!,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Button(
                onClick = {
                    scope.launch {
                        error = null
                        info = null
                        isLoading = true
                        try {
                            val resp = authService.resetPassword(
                                ResetPasswordRequest(
                                    mobile = mobile,        // will serialize as "mobile_or_email"
                                    newPassword = newPass   // will serialize as "new_password"
                                )
                            )

                            if (resp.isSuccessful && (resp.body()?.success == true)) {
                                info = "Password updated successfully."

                                val encodedMobile = Uri.encode(mobile)

                                navController.navigate("login_form?mobile=$encodedMobile") {
                                    popUpTo("login_form") { inclusive = true }
                                }
                            } else {
                                error = resp.body()?.error
                                    ?: "Failed to reset password. Please try again."
                            }
                        } catch (e: Exception) {
                            error = "Something went wrong. Please try again."
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading && canSubmit,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Resetting...")
                } else {
                    Text("Reset Password")
                }
            }
        }
    }
}
