package com.example.fitlife.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.fitlife.domain.model.ExecutionLog
import com.example.fitlife.domain.model.ExecutionStatus
import com.example.fitlife.domain.repository.AutomationRepository
import com.example.fitlife.presentation.components.SimpleTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repo: AutomationRepository) : ViewModel() {
    val logs = mutableStateOf<List<ExecutionLog>>(emptyList())
    suspend fun load() {
        logs.value = repo.getHistory()
    }
}

@Composable
fun HistoryScreen(onBack: () -> Unit, viewModel: HistoryViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Scaffold(
        topBar = { SimpleTopBar("Execution History", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(viewModel.logs.value) { log ->
                HistoryItem(log)
            }
        }
    }
}

@Composable
fun HistoryItem(log: ExecutionLog) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C2D), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(log.workflowName, color = Color.White, fontWeight = FontWeight.Bold)
            Text(log.timestamp, color = Color.Gray, fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    if (log.status == ExecutionStatus.SUCCESS) Color(0xFF10B981) else Color(0xFFCF6679),
                    RoundedCornerShape(6.dp)
                )
        )
    }
}