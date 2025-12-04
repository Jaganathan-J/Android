package com.example.autoflow.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoflow.ui.theme.BackgroundDark
import com.example.autoflow.ui.theme.SurfaceDark
import com.example.autoflow.ui.theme.TextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogout: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(64.dp).background(Color.Gray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A", color = Color.White, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Alex", color = Color.White, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("alex@example.com", color = TextGrey)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            ListItem(headlineContent = { Text("Account", color = Color.White) })
            ListItem(headlineContent = { Text("Notifications", color = Color.White) })
            ListItem(headlineContent = { Text("Appearance", color = Color.White) })
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out", color = Color.Red)
            }
        }
    }
}

@Composable
fun ListItem(headlineContent: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        headlineContent()
        Text(">", color = TextGrey)
    }
    HorizontalDivider(color = SurfaceDark)
}