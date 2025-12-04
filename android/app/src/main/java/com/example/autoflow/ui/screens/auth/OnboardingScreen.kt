package com.example.autoflow.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoflow.ui.components.PrimaryButton
import com.example.autoflow.ui.theme.BackgroundDark
import com.example.autoflow.ui.theme.TextGrey

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Graphic Placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFF14142B), androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("AutoFlow", color = Color.White, fontSize = 24.sp)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Workflow Automation",
            color = Color.White,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Connect your favorite services and automate repetitive tasks in seconds.",
            color = TextGrey,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(text = "Get Started", onClick = onGetStarted)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onLogin) {
            Text("Log In", color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}