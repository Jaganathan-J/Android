package com.example.fitlife.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.* 
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.presentation.components.SimpleTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepo: AuthRepository) : ViewModel() {
    var user by mutableStateOf<User?>(null)
    init {
        viewModelScope.launch {
            user = authRepo.getCurrentUser()
        }
    }
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepo.logout()
            onSuccess()
        }
    }
}

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user

    Scaffold(
        topBar = { SimpleTopBar("Profile", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        Column(modifier = Modifier.padding(it).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.Gray))
            Spacer(modifier = Modifier.height(16.dp))
            Text(user?.name ?: "User", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(user?.email ?: "", color = Color.Gray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(48.dp))

            SettingItem("Notifications")
            SettingItem("Privacy")
            SettingItem("Help & Support")
            
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Log Out",
                color = Color(0xFFCF6679),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { viewModel.logout(onLogout) }
            )
        }
    }
}

@Composable
fun SettingItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = Color.White, fontSize = 16.sp)
        Text(">", color = Color.Gray)
    }
}