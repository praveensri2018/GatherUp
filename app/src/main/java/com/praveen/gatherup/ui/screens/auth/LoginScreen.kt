package com.praveen.gatherup.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.praveen.gatherup.ui.theme.Shapes

@Preview
@Composable
fun LoginScreen(navController: NavController) {
    var agreed by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    // Prefer navigateUp, fallback to login_form
                    if (!navController.popBackStack()) {
                        navController.navigate("login_form") {
                            popUpTo("splash") { inclusive = false }
                        }
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = colors.onBackground)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Legal & Privacy",
                    style = typography.titleLarge,
                    color = colors.onBackground
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .padding(horizontal = 28.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // circular icon
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                        .background(colors.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "shield",
                        tint = PurpleAccentColorOrFallback(colors),
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Before you continue",
                    style = typography.headlineMedium,
                    color = colors.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please review our legal policies to complete your account setup.",
                    style = typography.bodyLarge,
                    color = colors.onBackground.copy(alpha = 0.75f),
                    modifier = Modifier.padding(horizontal = 8.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))

                // List items (terms / privacy)
                PrivacyRow(
                    title = "Terms of Service",
                    onClick = { navController.navigate("terms") },
                    colors = colors,
                    typography = typography
                )
                Spacer(modifier = Modifier.height(18.dp))
                PrivacyRow(
                    title = "Privacy Policy",
                    onClick = { navController.navigate("privacy") },
                    colors = colors,
                    typography = typography
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // checkbox + agree button row (bottom)
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = agreed,
                        onCheckedChange = { agreed = it },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = colors.onBackground,
                            checkedColor = PurpleAccentColorOrFallback(colors),
                            checkmarkColor = colors.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "I have read and agree to the ",
                        color = colors.onBackground,
                        style = typography.bodyLarge
                    )
                    Text(
                        text = "Terms of Service",
                        color = PurpleAccentColorOrFallback(colors),
                        style = typography.bodyLarge,
                        modifier = Modifier.clickable { navController.navigate("terms") },
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = " and ",
                        color = colors.onBackground,
                        style = typography.bodyLarge
                    )
                    Text(
                        text = "Privacy Policy.",
                        color = PurpleAccentColorOrFallback(colors),
                        style = typography.bodyLarge,
                        modifier = Modifier.clickable { navController.navigate("privacy") },
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Pill primary button (disabled until checkbox)
                Button(
                    onClick = {
                        // navigate to register or login_form depending on your flow
                        // I navigate to "register" (you can change to "login_form")
                        navController.navigate("register") {
                            popUpTo("login") { inclusive = false }
                        }
                    },
                    enabled = agreed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        disabledContainerColor = colors.surface,
                        disabledContentColor = colors.onSurface.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = "Agree & Create Account",
                        style = typography.labelLarge,
                        color = if (agreed) colors.onPrimary else colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivacyRow(
    title: String,
    onClick: () -> Unit,
    colors: androidx.compose.material3.ColorScheme,
    typography: androidx.compose.material3.Typography
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // icon circle
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(colors.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = PurpleAccentColorOrFallback(colors)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = typography.titleLarge,
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = colors.onBackground.copy(alpha = 0.6f)
        )
    }
}

/**
 * Helper to obtain the purple accent color. Use theme token if present; fallback to literal.
 */
@Composable
private fun PurpleAccentColorOrFallback(colors: androidx.compose.material3.ColorScheme) =
    if (colors.secondary != Color.Unspecified) colors.secondary else androidx.compose.ui.graphics.Color(0xFF8C6EF2)
