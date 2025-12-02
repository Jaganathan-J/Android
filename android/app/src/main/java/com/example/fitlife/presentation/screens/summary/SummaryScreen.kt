package com.example.fitlife.presentation.screens.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.ui.theme.BrightCoral
import com.example.fitlife.ui.theme.DeepIndigo
import com.example.fitlife.ui.theme.Lavender

@Composable
fun SummaryScreen(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Summary", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Lavender),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Good Job!", fontSize = 24.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(24.dp))
                
                // Using a simple circle for summary stat placeholder
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                   Text("01:24", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DeepIndigo)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDone,
                    colors = ButtonDefaults.buttonColors(containerColor = BrightCoral)
                ) {
                    Text("Finish", color = Color.White)
                }
            }
        }
    }
}