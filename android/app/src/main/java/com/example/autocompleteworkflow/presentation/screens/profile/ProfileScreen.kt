package com.example.autocompleteworkflow.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.autocompleteworkflow.presentation.components.StandardTopBar
import com.example.autocompleteworkflow.presentation.theme.BackgroundDark
import com.example.autocompleteworkflow.presentation.theme.SurfaceDark

@Composable
fun ProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    Scaffold(
        topBar = { StandardTopBar("Profile", onBack) },
        containerColor = BackgroundDark
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            // Header
            Column(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(80.dp).background(Color.Gray.copy(0.3f), CircleShape))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Alex", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
                Text("alex@example.com", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            
            // Menu
            ProfileMenuItem(Icons.Default.Settings, "Account")
            ProfileMenuItem(Icons.Default.Notifications, "Notifications")
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark, contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth().padding(24.dp).height(50.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out")
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .background(SurfaceDark, MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
    }
}