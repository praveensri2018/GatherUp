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
fun ResetPasswordScreen(navController: NavController) {
    var newPass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Reset Password")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = newPass, onValueChange = { newPass = it }, label = { Text("New Password") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = confirm, onValueChange = { confirm = it }, label = { Text("Confirm Password") })
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { navController.navigate("auth_success") }) {
                Text("Reset Password")
            }
        }
    }
}
