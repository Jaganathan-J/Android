package com.example.fitlife.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.components.PrimaryButton
import com.example.fitlife.presentation.components.ScreenHeader
import com.example.fitlife.presentation.theme.DeepIndigo
import com.example.fitlife.presentation.theme.LightGray

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(LightGray)) {
        // Side purple bar decoration
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(12.dp)
                .background(DeepIndigo)
                .align(Alignment.CenterStart)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp) // Make room for bar
        ) {
            ScreenHeader(title = "Log In", onBack = onBack)
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                PrimaryButton(
                    text = "Log In",
                    onClick = onLoginSuccess,
                    backgroundColor = DeepIndigo
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Forgot password?", color = DeepIndigo)
                }
            }
        }
    }
}