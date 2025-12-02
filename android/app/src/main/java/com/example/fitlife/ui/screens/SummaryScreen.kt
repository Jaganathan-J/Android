package com.example.fitlife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.ui.theme.Coral40
import com.example.fitlife.ui.theme.Indigo40
import com.example.fitlife.ui.theme.IndigoLight
import com.example.fitlife.ui.theme.White

@Composable
fun SummaryScreen(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = IndigoLight.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Good job!", fontSize = 24.sp, color = Indigo40, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "01:24",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo40
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onDone,
                    colors = ButtonDefaults.buttonColors(containerColor = Coral40),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Done")
                }
            }
        }
    }
}