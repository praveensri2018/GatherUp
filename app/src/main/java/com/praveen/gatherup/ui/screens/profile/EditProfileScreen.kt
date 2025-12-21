package com.praveen.gatherup.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen() {

    var name by remember { mutableStateOf("Elara Vance") }
    var username by remember { mutableStateOf("@elara") }
    var bio by remember {
        mutableStateOf("Exploring the intersection of art and technology...")
    }
    var location by remember { mutableStateOf("Kyoto, Japan") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E1621))
            .padding(16.dp)
    ) {

        /* ---------- TOP BAR ---------- */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { }) {
                Icon(Icons.Default.ArrowBack, null)
            }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { }) {
                Text("Save")
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ---------- PHOTO ---------- */
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
            )
        }

        Text(
            "Change Photo",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(24.dp))

        /* ---------- FIELDS ---------- */
        ProfileInput("Full Name", name) { name = it }
        ProfileInput("Username", username) { username = it }
        ProfileInput("Bio", bio, minLines = 3) { bio = it }
        ProfileInput("Location", location) { location = it }

        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Make Profile Private")
            Spacer(Modifier.weight(1f))
            Switch(checked = false, onCheckedChange = {})
        }
    }
}

@Composable
private fun ProfileInput(
    label: String,
    value: String,
    minLines: Int = 1,
    onChange: (String) -> Unit
) {
    Column {
        Text(label, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(Modifier.height(12.dp))
    }
}
