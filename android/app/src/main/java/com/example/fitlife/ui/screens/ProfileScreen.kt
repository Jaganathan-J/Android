package com.example.fitlife.ui.screens

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
import com.example.fitlife.ui.theme.Indigo40
import com.example.fitlife.ui.theme.IndigoLight
import com.example.fitlife.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onNavigateTo: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onNavigateTo("back") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Indigo40)
                    }
                }
            )
        },
        containerColor = White
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(IndigoLight)
                ) // Placeholder avatar
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Emma Johnson", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("emme@email.com", color = Color.Gray, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            MenuButton("Workouts", Icons.Filled.PlayCircleFilled) { onNavigateTo("workouts") }
            MenuButton("Exercises", Icons.Filled.FitnessCenter) { onNavigateTo("workouts") }
            MenuButton("Progress", Icons.Filled.InsertChart) { onNavigateTo("summary") }
            MenuButton("Settings", Icons.Filled.Settings) { onNavigateTo("settings") }
        }
    }
}

@Composable
fun MenuButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = IndigoLight.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Indigo40)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Indigo40)
        }
    }
}