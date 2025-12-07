package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.model.SendOtpRequest
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var phone by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var info by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val authService = remember { NetworkModule.authService }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Forgot Password", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (info != null) {
                Text(text = info!!, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
            }

            Button(
                onClick = {
                    scope.launch {
                        error = null
                        info = null
                        isLoading = true
                        try {
                            val resp = authService.sendForgotOtp(SendOtpRequest(mobile = phone.trim()))
                            if (resp.isSuccessful && (resp.body()?.success == true)) {
                                info = "OTP sent to your mobile."
                                // go to OTP screen with mobile
                                navController.navigate("otp_verify/${phone.trim()}")
                            } else {
                                error = resp.body()?.error
                                    ?: "Failed to send OTP. Please try again."
                            }
                        } catch (e: Exception) {
                            error = "Something went wrong. Please try again."
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading && phone.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sending OTP...")
                } else {
                    Text("Request Reset")
                }
            }
        }
    }
}
