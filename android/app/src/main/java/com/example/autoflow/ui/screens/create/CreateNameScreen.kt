package com.example.autoflow.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.autoflow.ui.components.PrimaryButton
import com.example.autoflow.ui.theme.BackgroundDark
import com.example.autoflow.ui.theme.SurfaceDark
import com.example.autoflow.ui.theme.TextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNameScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text("Step 1 of 4", color = TextGrey, style = MaterialTheme.typography.bodyMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        ) {
            Text("Create Automation", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Automation Name", color = TextGrey)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                     focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = { Text("e.g. Email to Slack", color = TextGrey) }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            PrimaryButton(text = "Continue", onClick = onContinue, enabled = name.isNotEmpty())
        }
    }
}