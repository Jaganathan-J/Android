package com.example.autoflow.ui.screens.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoflow.domain.model.ServiceItem
import com.example.autoflow.domain.repository.WorkflowRepository
import com.example.autoflow.ui.theme.BackgroundDark
import com.example.autoflow.ui.theme.SurfaceDark
import com.example.autoflow.ui.theme.TextGrey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectServiceViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {
    var items by mutableStateOf<List<ServiceItem>>(emptyList())

    fun loadTriggers() {
        viewModelScope.launch {
            items = repository.getTriggers()
        }
    }

    fun loadActions() {
        viewModelScope.launch {
            items = repository.getActions()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectServiceScreen(
    isTrigger: Boolean,
    onSelected: () -> Unit,
    onBack: () -> Unit,
    viewModel: SelectServiceViewModel = hiltViewModel()
) {
    LaunchedEffect(isTrigger) {
        if (isTrigger) viewModel.loadTriggers() else viewModel.loadActions()
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text(if (isTrigger) "Step 2 of 4" else "Step 3 of 4", color = TextGrey, style = MaterialTheme.typography.bodyMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        ) {
            Text(if (isTrigger) "Choose a Trigger" else "Select an Action", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(viewModel.items) { item ->
                    ServiceCard(item, onClick = onSelected)
                }
            }
        }
    }
}

@Composable
fun ServiceCard(item: ServiceItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconByName(item.iconName),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(item.name, color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(item.description, color = TextGrey, fontSize = 12.sp)
            }
        }
    }
}

fun getIconByName(name: String): ImageVector {
    return when(name) {
        "Email" -> Icons.Default.Email
        "Schedule" -> Icons.Default.Schedule
        else -> Icons.Default.Notifications
    }
}