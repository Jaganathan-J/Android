package com.example.automationoverflow.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automationoverflow.domain.model.HistoryItem
import com.example.automationoverflow.domain.repository.WorkflowRepository
import com.example.automationoverflow.presentation.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {
    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getHistory().collect { _history.value = it }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onCreateNew: () -> Unit,
    onProfile: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val historyItems by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Execution History", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    IconButton(onClick = onProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateNew,
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        },
        containerColor = Background
    ) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp)) {
            items(historyItems) { item ->
                HistoryCard(item)
            }
        }
    }
}

@Composable
fun HistoryCard(item: HistoryItem) {
    val badgeColor = when (item.status) {
        "Success" -> BadgeSuccess
        "Running" -> BadgeRunning
        else -> BadgeFail
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.workflowName, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text(
                    if(item.status == "Running") "Running..." else "Completed", 
                    style = MaterialTheme.typography.bodySmall, 
                    color = TextSecondary
                )
            }
            Box(
                modifier = Modifier
                    .background(badgeColor.copy(alpha = 0.2f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(item.status, color = badgeColor, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}