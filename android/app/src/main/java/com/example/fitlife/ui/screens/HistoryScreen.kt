package com.example.fitlife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fitlife.domain.model.ExecutionStatus
import com.example.fitlife.domain.repository.AutomationRepository
import com.example.fitlife.ui.components.PrimaryButton
import com.example.fitlife.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    repository: AutomationRepository
) : ViewModel() {
    val history = repository.getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {
    val history by viewModel.history.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("History", style = MaterialTheme.typography.headlineLarge, color = TextWhite)
            // Profile Icon Placeholder
            Box(Modifier.size(32.dp).background(Color.Gray, RoundedCornerShape(16.dp)))
        }

        LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.weight(1f)) {
            items(history) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(SurfaceDark, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(item.automationName, color = TextWhite, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${item.timestamp} • ${item.details}", 
                            color = TextGrey, 
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Badge(
                        containerColor = when(item.status) {
                            ExecutionStatus.SUCCESS -> AccentGreen
                            ExecutionStatus.FAILED -> AccentRed
                            ExecutionStatus.PENDING -> AccentYellow
                        }
                    ) {
                        Text(item.status.name, color = BackgroundDark, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
        
        PrimaryButton(
            text = "+ New Automation",
            onClick = { navController.navigate("create_wizard") },
            modifier = Modifier.padding(24.dp)
        )
    }
}