package com.example.fitlife.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.components.ScreenHeader
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LightGray

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
    ) {
        Box(modifier = Modifier.background(Color.White)) {
            ScreenHeader(title = "Settings", onBack = onBack)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
        ) {
            SettingsItem(title = "Notification")
            HorizontalDivider(color = LightGray)
            SettingsItem(title = "Reminders")
            HorizontalDivider(color = LightGray)
            SettingsItem(title = "Units")
            HorizontalDivider(color = LightGray)
            SettingsItem(title = "Privacy Policy")
        }
    }
}

@Composable
fun SettingsItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, color = DeepIndigo)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = DeepIndigo)
    }
}