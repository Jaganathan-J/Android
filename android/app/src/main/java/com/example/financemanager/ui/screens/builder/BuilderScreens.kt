package com.example.financemanager.ui.screens.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanager.domain.model.IntegrationItem
import com.example.financemanager.ui.components.AppTextField
import com.example.financemanager.ui.components.GradientButton
import com.example.financemanager.ui.theme.*

// --- Screen 1: Naming ---
@Composable
fun AutomationNameScreen(
    viewModel: WorkflowBuilderViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val name by viewModel.workflowName.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextWhite, modifier = Modifier.clickable(onClick = onBack))
        Spacer(Modifier.height(32.dp))
        Text("Name your Automation", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        AppTextField(value = name, onValueChange = viewModel::setName, label = "Automation Name")
        Spacer(Modifier.weight(1f))
        GradientButton(text = "Continue", onClick = onContinue, enabled = name.isNotBlank())
    }
}

// --- Screen 2 & 3: Selection (Generic) ---
@Composable
fun TriggerSelectScreen(viewModel: WorkflowBuilderViewModel, onTriggerSelected: () -> Unit, onBack: () -> Unit) {
    val items by viewModel.availableTriggers.collectAsState()
    SelectionListScreen(title = "Choose a Trigger", items = items, onBack = onBack) {
        viewModel.selectTrigger(it)
        onTriggerSelected()
    }
}

@Composable
fun ActionSelectScreen(viewModel: WorkflowBuilderViewModel, onActionSelected: () -> Unit, onBack: () -> Unit) {
    val items by viewModel.availableActions.collectAsState()
    SelectionListScreen(title = "Select an Action", items = items, onBack = onBack) {
        viewModel.selectAction(it)
        onActionSelected()
    }
}

@Composable
fun SelectionListScreen(
    title: String,
    items: List<IntegrationItem>,
    onBack: () -> Unit,
    onSelect: (IntegrationItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextWhite, modifier = Modifier.clickable(onClick = onBack))
        Spacer(Modifier.height(24.dp))
        Text(title, color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items) { item ->
                IntegrationCard(item = item, onClick = { onSelect(item) })
            }
        }
    }
}

@Composable
fun IntegrationCard(item: IntegrationItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceColor)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(InputBackground, CircleShape)
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (item.iconName == "email") Icons.Default.Email else Icons.Default.Storage,
                contentDescription = null,
                tint = TextWhite
            )
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(item.name, color = TextWhite, fontWeight = FontWeight.Bold)
            Text("Integration", color = TextGrey, fontSize = 12.sp)
        }
    }
}

// --- Screen 4: Preview ---
@Composable
fun WorkflowPreviewScreen(
    viewModel: WorkflowBuilderViewModel,
    onSaveSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val trigger by viewModel.selectedTrigger.collectAsState()
    val action by viewModel.selectedAction.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp)) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextWhite, modifier = Modifier.clickable(onClick = onBack))
        Spacer(Modifier.height(24.dp))
        Text("Review Automation", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        
        Spacer(Modifier.weight(1f))
        
        // Visualization Node Path
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            NodeView(title = trigger?.name ?: "Trigger", icon = Icons.Default.Email)
            Box(modifier = Modifier.height(60.dp).width(2.dp).background(Color.Gray))
            NodeView(title = action?.name ?: "Action", icon = Icons.Default.Storage)
        }
        
        Spacer(Modifier.weight(1f))
        GradientButton(text = "Save Automation", onClick = { viewModel.saveWorkflow(onSaveSuccess) }, isLoading = isSaving)
    }
}

@Composable
fun NodeView(title: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(SurfaceColor, CircleShape)
                .border(2.dp, PrimaryBlue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = TextWhite, modifier = Modifier.size(32.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(title, color = TextWhite, fontWeight = FontWeight.Medium)
    }
}