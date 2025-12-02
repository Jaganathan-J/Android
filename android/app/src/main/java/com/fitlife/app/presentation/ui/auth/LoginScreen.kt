package com.fitlife.app.presentation.ui.auth

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.presentation.theme.DeepIndigo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Matches Screen 2: White content, Back arrow, "Log In" Title
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = DeepIndigo,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Log In",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 32.sp),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(48.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DeepIndigo),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Log In", fontSize = 18.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Forgot password?",
                color = DeepIndigo,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}