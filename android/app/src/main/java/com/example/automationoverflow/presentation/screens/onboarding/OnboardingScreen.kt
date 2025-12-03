package com.example.automationoverflow.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.automationoverflow.presentation.components.GradientButton
import com.example.automationoverflow.presentation.theme.Background
import com.example.automationoverflow.presentation.theme.PrimaryBlue
import com.example.automationoverflow.presentation.theme.TextSecondary

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Automation Overflow",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Connect your apps and automate your daily workflow with zero coding.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        GradientButton(text = "Get Started", onClick = onGetStarted)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Log In",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            modifier = Modifier.clickable { onLogin() }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}