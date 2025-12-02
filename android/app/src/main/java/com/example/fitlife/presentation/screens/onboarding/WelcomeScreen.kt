package com.example.fitlife.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.ui.theme.DeepIndigo
import com.example.fitlife.ui.theme.BrightCoral
import com.example.fitlife.ui.theme.White

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepIndigo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.weight(1f))
        // Illustration Placeholder
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Illustration", color = White)
        }
        Spacer(modifier = Modifier.weight(0.5f))
        
        Text(
            text = "Welcome to FitLife",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Achieve your fitness goals with personalized workouts",
            style = MaterialTheme.typography.bodyLarge,
            color = White.copy(alpha = 0.8f)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrightCoral),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Get Started", fontSize = 18.sp, color = White)
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}