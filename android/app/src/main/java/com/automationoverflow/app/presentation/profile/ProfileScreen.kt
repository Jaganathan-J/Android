package com.automationoverflow.app.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.automationoverflow.app.presentation.theme.AppSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Files & Settings") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Column(Modifier.padding(it).padding(16.dp)) {
            // Profile Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    color = Color.Gray
                ) { Icon(Icons.Default.Person, null, margin = Modifier.padding(12.dp)) }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Alex", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Text("alex@example.com", color = Color.Gray)
                }
            }
            
            Spacer(Modifier.height(32.dp))
            
            SettingsItem(Icons.Default.Person, "Account")
            SettingsItem(Icons.Default.Notifications, "Notifications")
            SettingsItem(Icons.Default.Palette, "Appearance")
            
            Spacer(Modifier.weight(1f))
            
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = AppSurface, contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, null)
                Spacer(Modifier.width(8.dp))
                Text("Log Out")
            }
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray)
        Spacer(Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
    }
}

fun Icon(imageVector: ImageVector, contentDescription: String?, margin: Modifier = Modifier) {
    androidx.compose.material3.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = margin
    )
}