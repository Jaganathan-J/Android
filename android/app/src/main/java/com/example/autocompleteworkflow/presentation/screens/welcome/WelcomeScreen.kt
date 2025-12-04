package com.example.autocompleteworkflow.presentation.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autocompleteworkflow.presentation.components.PrimaryButton
import com.example.autocompleteworkflow.presentation.theme.BackgroundDark

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Illustration Placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.1f), shape = MaterialTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Text("Illustration", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Autocomplete\nWorkflow",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Automate your daily tasks with simple\ntrigger-action workflows.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(text = "Get Started", onClick = onGetStarted)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onLogin) {
            Text("Log In", color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}