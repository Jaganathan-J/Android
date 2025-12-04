package com.example.autoflow.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoflow.ui.components.PrimaryButton
import com.example.autoflow.ui.theme.BackgroundDark
import com.example.autoflow.ui.theme.SurfaceDark
import com.example.autoflow.ui.theme.TextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkflowPreviewScreen(onSave: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text("Step 4 of 4", color = TextGrey, style = MaterialTheme.typography.bodyMedium) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Workflow Preview", style = MaterialTheme.typography.headlineLarge, color = Color.White, modifier = Modifier.align(Alignment.Start))
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Visual Chain
            PreviewCard("Receive Email", "Trigger", Icons.Default.Email)
            
            Box(modifier = Modifier.height(32.dp).width(2.dp).background(TextGrey))
            
            PreviewCard("Send Slack Message", "Action", Icons.Default.Notifications)
            
            Spacer(modifier = Modifier.weight(1f))
            
            PrimaryButton(text = "Save Automation", onClick = onSave)
        }
    }
}

@Composable
fun PreviewCard(title: String, type: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(com.example.autoflow.ui.theme.PrimaryPurple.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = com.example.autoflow.ui.theme.PrimaryPurple)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(type.uppercase(), color = TextGrey, fontSize = 10.sp)
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}