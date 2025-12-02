package com.example.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitlife.ui.theme.Indigo40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Indigo40)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            val settings = listOf("Notification", "Reminders", "Units", "Privacy Policy")
            settings.forEach { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
                    modifier = Modifier.clickable { }
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}