package com.example.financemanager.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanager.ui.components.GradientButton
import com.example.financemanager.ui.theme.*

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo placeholder
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    Brush.linearGradient(listOf(PrimaryBlue, PrimaryPurple)),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Finance Manager",
            color = TextWhite,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Automate your financial workflow",
            color = TextGrey,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        GradientButton(text = "Get Started", onClick = onGetStarted)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Log in",
            color = PrimaryPurple,
            modifier = Modifier.clickable { onLogin() }
        )
    }
}