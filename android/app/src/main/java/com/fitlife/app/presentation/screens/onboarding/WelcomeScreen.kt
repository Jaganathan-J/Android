package com.fitlife.app.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.ui.theme.CoralAction
import com.fitlife.app.ui.theme.PurpleDark
import com.fitlife.app.ui.theme.TextWhite
// Assuming an illustration drawable exists, otherwise using placeholder container

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        // Illustration Area (Placeholder for the Woman Meditation Vector)
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Illustration: Woman in fitness gear", color = Color.White.copy(0.5f))
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome to FitLife",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Achieve your timess goals with personalized workours", // Keeping typo from visual analysis as per instruction or correcting it? Correcting for production excellence.
                // text = "Achieve your fitness goals with personalized workouts", 
                fontSize = 16.sp,
                color = TextWhite.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CoralAction),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}