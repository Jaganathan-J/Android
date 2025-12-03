package com.automationoverflow.app.presentation.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.automationoverflow.app.presentation.components.GradientButton

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Placeholder for large branding icon
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Brand",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(120.dp)
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = "Automation\nOverflow",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Automate your entire workflow lifecycle without coding.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        GradientButton(
            text = "Get Started",
            onClick = onGetStarted,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Log In",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.clickable { onLogin() }.padding(8.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}