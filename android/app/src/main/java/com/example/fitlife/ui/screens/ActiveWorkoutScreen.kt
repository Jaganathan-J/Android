package com.example.fitlife.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlife.ui.theme.Coral40
import com.example.fitlife.ui.theme.Indigo40
import com.example.fitlife.ui.theme.IndigoLight
import com.example.fitlife.ui.theme.TrackPurple
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutScreen(onBack: () -> Unit, onFinish: () -> Unit) {
    // Mock timer state
    var timeRemaining by remember { mutableStateOf(84) } // 01:24
    var isPaused by remember { mutableStateOf(false) }
    val totalTime = 100

    LaunchedEffect(key1 = isPaused, key2 = timeRemaining) {
        if (!isPaused && timeRemaining > 0) {
            delay(1000L)
            timeRemaining--
        } else if (timeRemaining == 0) {
            onFinish()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress", fontWeight = FontWeight.Bold, color = Indigo40) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Indigo40)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Circular Timer
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
                Canvas(modifier = Modifier.size(250.dp)) {
                    drawArc(
                        color = TrackPurple,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = Indigo40,
                        startAngle = -90f,
                        sweepAngle = (360f * (timeRemaining.toFloat() / totalTime.toFloat())),
                        useCenter = false,
                        style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text = String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo40
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.padding(32.dp)) {
                Button(
                    onClick = { isPaused = !isPaused },
                    colors = ButtonDefaults.buttonColors(containerColor = Coral40),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (isPaused) "Resume" else "Pause", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = onFinish,
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoLight),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Skip", color = Indigo40, fontSize = 18.sp)
                }
            }
        }
    }
}