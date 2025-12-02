package com.example.fitlife.presentation.screens.execution

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.autoMirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveSessionScreen(onBack: () -> Unit, onSummary: () -> Unit) {
    var timeLeft by remember { mutableIntStateOf(84) } // 1:24
    var isPaused by remember { mutableStateOf(false) }
    val totalTime = 84f

    LaunchedEffect(isPaused, timeLeft) {
        if (!isPaused && timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else if (timeLeft == 0) {
            onSummary()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
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
            Spacer(modifier = Modifier.weight(1f))

            // Timer Circle
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { timeLeft / totalTime },
                    modifier = Modifier.size(250.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.tertiary,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Button(
                    onClick = { isPaused = !isPaused },
                    modifier = Modifier.width(140.dp).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (isPaused) "Resume" else "Pause", fontSize = 18.sp)
                }

                Button(
                    onClick = onSummary,
                    modifier = Modifier.width(140.dp).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Skip", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
                }
            }
        }
    }
}