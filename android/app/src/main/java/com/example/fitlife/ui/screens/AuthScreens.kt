package com.example.fitlife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitlife.ui.components.CustomTextField
import com.example.fitlife.ui.components.PrimaryButton
import com.example.fitlife.ui.theme.BackgroundDark
import com.example.fitlife.ui.theme.PrimaryPurple
import com.example.fitlife.ui.theme.TextGrey
import com.example.fitlife.ui.theme.TextWhite

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(PrimaryPurple, MaterialTheme.shapes.large)
        ) // Placeholder for logo
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Automate your life",
            style = MaterialTheme.typography.headlineLarge,
            color = TextWhite
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Connect your apps and devices seamlessly.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextGrey
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(text = "Get Started", onClick = { navController.navigate("login") })
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Log In",
            color = TextGrey,
            modifier = Modifier.clickable { navController.navigate("login") }
        )
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineLarge,
            color = TextWhite,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        CustomTextField(value = email, onValueChange = { email = it }, label = "Email Address")
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
        
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot password?",
            color = PrimaryPurple,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(
            text = "Log In",
            onClick = { navController.navigate("history") },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        )
    }
}