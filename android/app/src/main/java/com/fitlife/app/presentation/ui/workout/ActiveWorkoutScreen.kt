package com.fitlife.app.presentation.ui.workout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitlife.app.presentation.theme.*
import kotlinx.coroutines.delay

@Composable
fun ActiveWorkoutScreen(onBack: () -> Unit, onSkip: () -> Unit) {
    var timeLeft by remember { mutableStateOf(84) } // 01:24 = 84s
    val totalTime = 100 // Arbitrary total for progress visual
    var isPaused by remember { mutableStateOf(false) }

    // Timer Logic for Screen 8
    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
        if (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0) {
            onSkip()
        }
    }

    // Screen 8 UI: Progress Circle, Timer, Pause/Skip Buttons
    Column(
        modifier = Modifier.fillMaxSize().background(White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = DeepIndigo,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text("810...", color = DeepIndigo, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Progress", style = MaterialTheme.typography.headlineMedium, color = DeepIndigo)
        Spacer(modifier = Modifier.height(48.dp))

        // Custom Circular Progress
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(250.dp)) {
                // Background Track (Light Purple)
                drawCircle(
                    color = ActiveProgressTrack,
                    style = Stroke(width = 40f, cap = StrokeCap.Round)
                )
                // Progress Fill (Deep Blue)
                drawArc(
                    color = ActiveProgressFill,
                    startAngle = -90f,
                    sweepAngle = (timeLeft.toFloat() / totalTime.toFloat()) * 360f,
                    useCenter = false,
                    style = Stroke(width = 40f, cap = StrokeCap.Round)
                )
            }
            Text(
                text = formatTime(timeLeft),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = DeepIndigo
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Control Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { isPaused = !isPaused },
                colors = ButtonDefaults.buttonColors(containerColor = CoralOrange),
                modifier = Modifier.weight(1f).height(56.dp).padding(end = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (isPaused) "Resume" else "Pause", fontSize = 18.sp)
            }
            Button(
                onClick = onSkip,
                colors = ButtonDefaults.buttonColors(containerColor = SkipButton),
                modifier = Modifier.weight(1f).height(56.dp).padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Skip", color = DeepIndigo, fontSize = 18.sp)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}