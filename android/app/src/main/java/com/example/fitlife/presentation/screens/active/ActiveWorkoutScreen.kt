package com.example.fitlife.presentation.screens.active

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor() : ViewModel() {
    var timeLeft by mutableIntStateOf(90)
    var totalTime by mutableIntStateOf(90)
    var isPaused by mutableStateOf(false)

    init {
        viewModelScope.launch {
            while (timeLeft > 0) {
                delay(1000)
                if (!isPaused) {
                    timeLeft--
                }
            }
        }
    }
    
    fun togglePause() { isPaused = !isPaused }
}

@Composable
fun ActiveWorkoutScreen(
    viewModel: ActiveWorkoutViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onFinish: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Text("Progress", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(16.dp))

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().weight(1f)) {
            val progress = viewModel.timeLeft / viewModel.totalTime.toFloat()
            CircularTimer(progress)
            Text(
                text = String.format("%02d:%02d", viewModel.timeLeft / 60, viewModel.timeLeft % 60),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = DeepIndigo
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { viewModel.togglePause() },
                colors = ButtonDefaults.buttonColors(containerColor = PauseButton),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(56.dp).padding(end = 8.dp)
            ) {
                Text(if (viewModel.isPaused) "Resume" else "Pause", fontSize = 18.sp)
            }
            Button(
                onClick = onFinish,
                colors = ButtonDefaults.buttonColors(containerColor = SkipButton),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(56.dp).padding(start = 8.dp)
            ) {
                Text("Skip", fontSize = 18.sp, color = DeepIndigo)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CircularTimer(progress: Float) {
    Canvas(modifier = Modifier.size(240.dp)) {
        drawCircle(
            color = ProgressTrack,
            style = Stroke(width = 24.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = ProgressFill,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(width = 24.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}