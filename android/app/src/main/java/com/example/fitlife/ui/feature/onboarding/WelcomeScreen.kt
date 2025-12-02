package com.example.fitlife.ui.feature.onboarding

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitlife.ui.navigation.Screen
import com.example.fitlife.ui.theme.BrandCoral
import com.example.fitlife.ui.theme.BrandPurple
// Placeholder generic vector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter

@Composable
fun WelcomeScreen(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandPurple)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Illustration Placeholder
        Image(
            imageVector = Icons.Default.FitnessCenter, 
            contentDescription = "Fitness Illustration",
            modifier = Modifier.size(200.dp).padding(bottom = 32.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White.copy(alpha=0.8f))
        )

        Text(
            text = "Welcome to FitLife",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Achieve your fitness goals with personalized workouts",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 48.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController?.navigate(Screen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandCoral),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Get Started", fontSize = 18.sp, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}