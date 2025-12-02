package com.example.fitlife.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.ui.theme.Lavender
import com.example.fitlife.ui.theme.DeepIndigo

@Composable
fun ProfileScreen(
    onNavigateToWorkouts: () -> Unit,
    onNavigateToExercises: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        IconButton(onClick = { /* Handle back if needed */ }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Profile Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Emma Johnson", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text("emme@email.com", color = Color.Gray, fontSize = 14.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Menu
        ProfileMenuItem("Workouts", Icons.Default.PlayCircleFilled, onNavigateToWorkouts)
        ProfileMenuItem("Exercises", Icons.Default.FitnessCenter, onNavigateToExercises)
        ProfileMenuItem("Progress", Icons.Default.InsertChart, onNavigateToProgress)
        ProfileMenuItem("Settings", Icons.Default.Settings, onNavigateToSettings)
    }
}

@Composable
fun ProfileMenuItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Lavender),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = DeepIndigo)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}