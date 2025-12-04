package com.example.fitlifefinance.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenLight
import com.example.fitlifefinance.ui.theme.GreenPrimary

@Composable
fun ProfileScreen(navController: NavController, onSignOut: () -> Unit) {
    Scaffold(
        bottomBar = { FitLifeBottomBar(Routes.PROFILE, { navController.navigate(it) }) }
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(64.dp).background(GreenLight, CircleShape), contentAlignment = Alignment.Center) {
                    Text("JD", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("John Doe", style = MaterialTheme.typography.titleLarge)
                    Text("john.doe@email.com", color = Color.Gray)
                    SuggestionChip(onClick = {}, label = { Text("Premium Plan", color = GreenPrimary) }, colors = SuggestionChipDefaults.suggestionChipColors(containerColor = GreenLight.copy(alpha=0.3f)))
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {}) { Text("Edit", color = GreenPrimary) }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Account Settings", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            ProfileItem("Personal Information")
            ProfileItem("Security & Privacy")
            ProfileItem("Linked Bank Accounts")
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Preferences", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            ProfileItem("Notification Preferences")
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
                Text("Dark Mode", modifier = Modifier.weight(1f))
                Switch(checked = false, onCheckedChange = {})
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSignOut,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Sign Out")
            }
        }
    }
}

@Composable
fun ProfileItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text)
        Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
    }
    HorizontalDivider(color = Color(0xFFEEEEEE))
}