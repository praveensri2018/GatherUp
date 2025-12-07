package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.data.NetworkModule
import com.praveen.gatherup.data.model.VerifyOtpRequest
import kotlinx.coroutines.launch

@Composable
fun OtpVerifyScreen(navController: NavController, mobile: String) {
    var otp by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val authService = remember { NetworkModule.authService }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "OTP Verify", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "OTP sent to: $mobile")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Enter OTP") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(4.dp))
            }

            Button(
                onClick = {
                    scope.launch {
                        error = null
                        isLoading = true
                        try {
                            val resp = authService.verifyForgotOtp(
                                VerifyOtpRequest(
                                    mobile = mobile,
                                    otp = otp.trim()
                                )
                            )
                            if (resp.isSuccessful && (resp.body()?.success == true)) {
                                // OTP ok -> go to reset password with same mobile
                                navController.navigate("reset_password/$mobile") {
                                    popUpTo("forgot_password") { inclusive = false }
                                }
                            } else {
                                error = resp.body()?.error ?: "Invalid OTP. Try again."
                            }
                        } catch (e: Exception) {
                            error = "Something went wrong. Please try again."
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading && otp.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Verifying...")
                } else {
                    Text("Verify")
                }
            }
        }
    }
}
