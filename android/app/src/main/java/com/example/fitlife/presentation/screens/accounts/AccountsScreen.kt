package com.example.fitlife.presentation.screens.accounts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.presentation.components.GradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(onBack: () -> Unit) {
    val accounts = listOf(
        "Main Checking" to "Checking",
        "Savings" to "Savings",
        "Visa Card" to "Credit"
    )

    GradientBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text("Your Accounts", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            LazyColumn(
                modifier = Modifier.padding(16.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(accounts) { account ->
                    Card(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(account.first, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                            Text(account.second, fontSize = 14.sp, color = Color.LightGray)
                        }
                    }
                }
            }
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("+ Add Account", fontWeight = FontWeight.Bold)
            }
        }
    }
}