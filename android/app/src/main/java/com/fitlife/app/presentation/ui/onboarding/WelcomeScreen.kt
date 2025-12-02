package com.fitlife.app.presentation.ui.onboarding

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
import com.fitlife.app.presentation.theme.CoralOrange
import com.fitlife.app.presentation.theme.DeepIndigo

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    // Matches Screen 1: Deep Purple BG, Illustration (Placeholder), Title, Subtitle, Button
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepIndigo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.weight(1f))
        // Illustration Placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Welcome to FitLife",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Achieve your fitness goals with personalized workouts",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CoralOrange),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}