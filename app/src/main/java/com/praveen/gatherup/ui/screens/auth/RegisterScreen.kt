package com.praveen.gatherup.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

private const val TAG = "RegisterScreen"

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    apiError: String? = null, // <-- NEW
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
    val mobileValid = mobile.length in 10..15

    val canRegister =
        name.isNotBlank() && mobileValid && passwordValid && passwordsMatch

    // interaction source for clickable text (needed for explicit indication overload)
    val loginInteractionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(colors.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = "Logo",
                tint = colors.primary,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "GatherUp",
            style = typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Create your account",
            style = typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Person, null) }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { input ->
                mobile = input.filter { it.isDigit() }.take(15)
            },
            placeholder = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Call, null) }
        )

        if (!mobileValid && mobile.isNotEmpty()) {
            Text("Enter valid number", color = colors.error)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (pwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { pwdVisible = !pwdVisible }) {
                    Icon(
                        imageVector = if (pwdVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(Modifier.height(6.dp))

        Text("At least 8 characters", color = colors.onSurfaceVariant)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            placeholder = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        if (!passwordsMatch && confirm.isNotEmpty()) {
            Text("Password mismatch", color = colors.error)
        }

        // ---- API ERROR (like "user already exists") ----
        if (!apiError.isNullOrBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = apiError,
                color = colors.error,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                try {
                    onRegister(name.trim(), mobile.trim(), password)
                } catch (e: Exception) {
                    Log.e(TAG, "Registration Error", e)
                }
            },
            enabled = canRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Register", color = Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Row {
            Text("Already have an account? ")
            Text(
                text = "Login",
                color = colors.primary,
                modifier = Modifier.clickable(
                    interactionSource = loginInteractionSource,
                    indication = LocalIndication.current
                ) {
                    try {
                        onLoginClick()
                    } catch (e: Exception) {
                        Log.e(TAG, "Login click error", e)
                    }
                }
            )
        }
    }
}

@Composable
private fun PurpleAccentColorOrFallback(colors: androidx.compose.material3.ColorScheme) =
    if (colors.secondary != Color.Unspecified) colors.secondary else Color(0xFF8C6EF2)

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RegisterScreenPreviewLight() {
    MaterialTheme {
        RegisterScreen(
            apiError = "user already exists",
            onRegister = { _, _, _ -> },
            onLoginClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegisterScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        RegisterScreen(
            apiError = "user already exists",
            onRegister = { _, _, _ -> },
            onLoginClick = {}
        )
    }
}
