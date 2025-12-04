package com.example.fitlifefinance.ui.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.fitlifefinance.ui.theme.GreenPrimary

@Composable
fun NotificationsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = { TextButton(onClick = {}) { Text("Mark all read", color = GreenPrimary) } }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            // Mock content
            Text("New", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
            ListItem(headlineContent = { Text("Salary Received!") }, supportingContent = { Text("You received $4,500.00") }, leadingContent = { Icon(Icons.Default.ArrowBack, null) })
        }
    }
}