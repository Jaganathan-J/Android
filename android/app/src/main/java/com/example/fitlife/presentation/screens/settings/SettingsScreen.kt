package com.example.fitlife.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.autoMirrored.filled.ArrowBack
import androidx.compose.material.icons.autoMirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            val items = listOf("Notification", "Reminders", "Units", "Privacy Policy")
            items(items.size) { index ->
                SettingItem(items[index])
                HorizontalDivider(color = LightGray)
            }
        }
    }
}

@Composable
fun SettingItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { } // No op
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, fontSize = 16.sp)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.Gray)
    }
}

val LightGray = androidx.compose.ui.graphics.Color(0xFFEBEBEB)
val Color = androidx.compose.ui.graphics.Color