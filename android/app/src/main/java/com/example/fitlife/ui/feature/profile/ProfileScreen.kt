package com.example.fitlife.ui.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.navigation.NavController
import com.example.fitlife.ui.navigation.Screen
import com.example.fitlife.ui.theme.BrandLavender
import com.example.fitlife.ui.theme.BrandPurple

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Header
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Emma Johnson", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("emme@email.com", fontSize = 14.sp, color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Menu
        val menuItems = listOf(
            Triple("Workouts", Icons.Default.PlayCircle, Screen.Categories.route),
            Triple("Exercises", Icons.Default.FitnessCenter, Screen.Categories.route),
            Triple("Progress", Icons.Default.InsertChart, Screen.Summary.route),
            Triple("Settings", Icons.Default.Settings, Screen.Settings.route)
        )
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(menuItems.size) { index ->
                val (label, icon, route) = menuItems[index]
                ProfileMenuItem(label, icon) {
                    navController.navigate(route)
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BrandLavender)
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = BrandPurple)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
    }
}