package com.fitlife.app.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.ui.theme.LightGrayBg
import com.fitlife.app.ui.theme.PurplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        // Left Sidebar Strip
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .background(PurplePrimary)
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayBg)
        ) {
            // White Card Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 48.dp)
            ) {
                // Header
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = PurplePrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Log In",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Form
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = PurplePrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = PurplePrimary
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Forgot password?",
                    color = PurplePrimary,
                    modifier = Modifier.align(Alignment.CenterHorizontally).clickable { /* TODO */ }
                )
            }
        }
    }
}