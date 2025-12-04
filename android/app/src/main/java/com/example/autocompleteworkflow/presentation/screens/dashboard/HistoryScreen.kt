package com.example.autocompleteworkflow.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.autocompleteworkflow.domain.model.RunStatus
import com.example.autocompleteworkflow.domain.model.Workflow
import com.example.autocompleteworkflow.domain.repository.WorkflowRepository
import com.example.autocompleteworkflow.presentation.theme.BackgroundDark
import com.example.autocompleteworkflow.presentation.theme.SuccessGreen
import com.example.autocompleteworkflow.presentation.theme.SurfaceDark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {
    private val _workflows = MutableStateFlow<List<Workflow>>(emptyList())
    val workflows = _workflows.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWorkflows().collect { _workflows.value = it }
        }
    }

    fun toggle(id: String, enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleWorkflow(id, enabled)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onCreateClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val workflows by viewModel.workflows.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Execution History", color = Color.White, fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateClick, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Create", tint = Color.White)
            }
        },
        containerColor = BackgroundDark
    ) {
        LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(workflows) { workflow ->
                HistoryCard(workflow, onToggle = { v -> viewModel.toggle(workflow.id, v) })
            }
        }
    }
}

@Composable
fun HistoryCard(workflow: Workflow, onToggle: (Boolean) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(workflow.name, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(workflow.lastRunTime, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = workflow.isEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (workflow.lastRunStatus != com.example.autocompleteworkflow.domain.model.RunStatus.NONE) {
                    StatusBadge(workflow.lastRunStatus)
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: RunStatus) {
    val (color, text) = when(status) {
        RunStatus.SUCCESS -> SuccessGreen to "Success"
        RunStatus.FAILED -> Color.Red to "Failed"
        else -> Color.Gray to "Pending"
    }
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(50),
        contentColor = color
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}