package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegister: (name: String, mobile: String, password: String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    var pwdVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val passwordsMatch = password == confirm
    val passwordValid = password.length >= 8
    val canRegister = name.isNotBlank() && mobile.isNotBlank() && passwordValid && passwordsMatch

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Logo
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(colors.primary.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {

                Icon(
                    imageVector = Icons.Default.Groups,
                    contentDescription = "Logo",
                    tint = colors.primary,
                    modifier = Modifier.size(36.dp)
                )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "GatherUp",
            style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your account",
            style = typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Join the community and start connecting.",
            style = typography.bodyMedium,
            color = colors.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Full Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Enter your full name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone Number
        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            placeholder = { Text("Enter your phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Call, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            trailingIcon = {
                IconButton(onClick = { pwdVisible = !pwdVisible }) {
                    Icon(
                        imageVector = if (pwdVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (pwdVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Must be at least 8 characters.",
            style = typography.bodySmall,
            color = colors.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password
        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            placeholder = { Text("Confirm your password") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (!passwordsMatch) {
            Text(
                text = "Passwords do not match.",
                color = colors.error,
                style = typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = { if (canRegister) onRegister(name.trim(), mobile.trim(), password) },
            enabled = canRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Register", color = Color.White)
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Login link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                style = typography.bodyMedium,
                color = colors.onBackground.copy(alpha = 0.7f)
            )
            Text(
                text = "Log in",
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = colors.primary,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/* ---------------- PREVIEWS ---------------- */

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RegisterScreenPreviewLight() {
    MaterialTheme {
        Surface {
            RegisterScreen()
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RegisterScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface {
            RegisterScreen()
        }
    }
}
