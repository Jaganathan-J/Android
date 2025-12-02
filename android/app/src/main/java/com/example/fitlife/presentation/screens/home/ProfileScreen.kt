package com.example.fitlife.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.* 
import androidx.compose.material3.* 
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LavenderLight

@Composable
fun ProfileScreen(onNavigateToWorkouts: () -> Unit, onNavigateToSettings: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = DeepIndigo,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Profile Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Emma Johnson", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
                Text("emme@email.com", fontSize = 14.sp, color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Menu
        ProfileMenuItem(
            icon = Icons.Default.PlayCircleFilled,
            text = "Workouts",
            onClick = onNavigateToWorkouts
        )
        ProfileMenuItem(
            icon = Icons.Default.FitnessCenter,
            text = "Exercises",
            onClick = { /* TODO */ }
        )
        ProfileMenuItem(
            icon = Icons.Default.InsertChart,
            text = "Progress",
            onClick = { /* TODO */ }
        )
        ProfileMenuItem(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = onNavigateToSettings
        )
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(LavenderLight)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = DeepIndigo)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
    }
}