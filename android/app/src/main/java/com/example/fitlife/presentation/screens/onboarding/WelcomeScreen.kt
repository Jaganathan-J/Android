package com.example.fitlife.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.components.PrimaryButton
import com.example.fitlife.presentation.theme.DeepIndigo

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepIndigo)
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        // Placeholder for the illustration (woman in fitness gear)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Illustration Placeholder", color = Color.White.copy(alpha = 0.5f))
        }
        
        Text(
            text = "Welcome to FitLife",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Achieve your fitness goals with personalized workouts",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        PrimaryButton(
            text = "Get Started",
            onClick = onGetStarted
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}