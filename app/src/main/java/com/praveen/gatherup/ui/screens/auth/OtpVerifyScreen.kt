package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OtpVerifyScreen(navController: NavController) {
    var otp by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "OTP Verify")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = otp, onValueChange = { otp = it }, label = { Text("Enter OTP") })
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { navController.navigate("auth_success") }) {
                Text("Verify")
            }
        }
    }
}
