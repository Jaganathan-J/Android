package com.example.automationoverflow.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.automationoverflow.presentation.components.AutomationTextField
import com.example.automationoverflow.presentation.components.GradientButton
import com.example.automationoverflow.presentation.theme.Background
import com.example.automationoverflow.presentation.theme.TextSecondary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(onSuccess: () -> Unit) {
        isLoading = true
        // simulate login
        androidx.lifecycle.viewModelScope.launch {
            delay(1000)
            isLoading = false
            onSuccess()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background
    ) {
        Column(
            modifier = Modifier.padding(it).padding(24.dp).fillMaxSize()
        ) {
            Text(text = "Log In", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))
            
            AutomationTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = "Email Address"
            )
            Spacer(modifier = Modifier.height(16.dp))
            AutomationTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = "Password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.clickable { }
            )
            Spacer(modifier = Modifier.height(32.dp))
            GradientButton(
                text = if (viewModel.isLoading) "Logging in..." else "Log In",
                onClick = { viewModel.login(onLoginSuccess) },
                enabled = !viewModel.isLoading && viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty()
            )
        }
    }
}