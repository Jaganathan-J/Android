package com.example.fitlife.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.presentation.components.FitLifeTextField
import com.example.fitlife.presentation.components.GradientButton
import com.example.fitlife.presentation.components.SimpleTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            val res = repo.login(email, password)
            isLoading = false
            if (res.isSuccess) onSuccess()
            else error = res.exceptionOrNull()?.message
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { SimpleTopBar("", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Log In",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            FitLifeTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                placeholder = "Email"
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            FitLifeTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                placeholder = "Password",
                visualTransformation = PasswordVisualTransformation()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            if (viewModel.error != null) {
                Text(viewModel.error!!, color = MaterialTheme.colorScheme.error)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            GradientButton(
                text = if (viewModel.isLoading) "Logging in..." else "Log In",
                onClick = { viewModel.login(onLoginSuccess) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isLoading
            )
        }
    }
}