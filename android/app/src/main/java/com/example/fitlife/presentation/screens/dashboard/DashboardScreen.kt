package com.example.fitlife.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Workflow
import com.example.fitlife.domain.repository.AutomationRepository
import com.example.fitlife.presentation.theme.PrimaryGradient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repo: AutomationRepository) : ViewModel() {
    val workflows = repo.getWorkflows()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@Composable
fun DashboardScreen(
    onCreateClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val workflows = viewModel.workflows.collectAsState().value

    Scaffold(
        floatingActionButton = {
             Box(
                 modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(PrimaryGradient)
                    .clickable(onClick = onCreateClick),
                 contentAlignment = Alignment.Center
             ) {
                 Icon(Icons.Default.Add, contentDescription = "Create", tint = Color.White)
             }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Add, "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onHistoryClick,
                    icon = { Icon(Icons.Default.History, "History") },
                    label = { Text("History") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { Icon(Icons.Default.Person, "Profile") },
                    label = { Text("Profile") }
                )
            }
        },
        containerColor = Color(0xFF0A0A16)
    ) {
        Column(
             modifier = Modifier.padding(it).padding(16.dp)
        ) {
            Text(
                "Active Workflows",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (workflows.isEmpty()) {
                Text("No automations yet. Create one!", color = Color.Gray)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(workflows) { wf ->
                        WorkflowCard(wf)
                    }
                }
            }
        }
    }
}

@Composable
fun WorkflowCard(wf: Workflow) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C2D)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
             Column(modifier = Modifier.weight(1f)) {
                 Text(wf.name, color = Color.White, fontWeight = FontWeight.Bold)
                 Text("${wf.trigger.title} → ${wf.action.title}", color = Color.Gray, fontSize = 12.sp)
             }
             Switch(checked = wf.isActive, onCheckedChange = {})
        }
    }
}