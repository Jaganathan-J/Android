package com.fitlife.app.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitlife.app.presentation.navigation.Screen
import com.fitlife.app.presentation.theme.DeepIndigo
import com.fitlife.app.presentation.theme.Lavender

@Composable
fun ProfileScreen(onNavigate: (String) -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val user = viewModel.userProfile.collectAsState().value

    // Matches Screen 3: Header, Profile Info, Menu List
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(24.dp).clickable { /* Handle Nav */ }
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(user?.name ?: "...", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(user?.email ?: "...", color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Menu Items calling Screens 5, 8, etc via routing
        ProfileMenuItem("Workouts", Icons.Default.PlayCircleFilled) { onNavigate(Screen.ActiveWorkout.route) }
        ProfileMenuItem("Exercises", Icons.Default.FitnessCenter) { onNavigate(Screen.Exercises.route) }
        ProfileMenuItem("Progress", Icons.Default.ShowChart) { /* Nav to Progress */ }
        ProfileMenuItem("Settings", Icons.Default.Settings) { /* Nav to Settings */ }
    }
}

@Composable
fun ProfileMenuItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Lavender.copy(alpha = 0.5f))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = DeepIndigo)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = DeepIndigo)
    }
}