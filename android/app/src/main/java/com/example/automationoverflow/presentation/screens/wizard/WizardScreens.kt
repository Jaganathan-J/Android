package com.example.automationoverflow.presentation.screens.wizard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.automationoverflow.presentation.components.AutomationTextField
import com.example.automationoverflow.presentation.components.GradientButton
import com.example.automationoverflow.presentation.components.SelectionCard
import com.example.automationoverflow.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background,
        content = content
    )
}

@Composable
fun WizardNameScreen(viewModel: WizardViewModel, onNext: () -> Unit, onBack: () -> Unit) {
    WizardScaffold(title = "Create Automation", onBack = onBack) {
        Column(modifier = Modifier.padding(it).padding(24.dp)) {
            Text("Name your skill", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(16.dp))
            AutomationTextField(value = viewModel.workflowName, onValueChange = { viewModel.workflowName = it }, label = "e.g. Daily Summary")
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(text = "Continue", onClick = onNext, enabled = viewModel.workflowName.isNotEmpty())
        }
    }
}

@Composable
fun WizardTriggerScreen(viewModel: WizardViewModel, onNext: () -> Unit, onBack: () -> Unit) {
    WizardScaffold(title = "Choose a Trigger", onBack = onBack) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp)) {
            items(viewModel.triggers) { trigger ->
                SelectionCard(
                    title = trigger.name,
                    subtitle = trigger.description,
                    icon = Icons.Default.Bolt,
                    onClick = {
                        viewModel.selectedTrigger = trigger
                        onNext()
                    }
                )
            }
        }
    }
}

@Composable
fun WizardActionScreen(viewModel: WizardViewModel, onNext: () -> Unit, onBack: () -> Unit) {
    WizardScaffold(title = "Select an Action", onBack = onBack) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp)) {
            items(viewModel.actions) { action ->
                SelectionCard(
                    title = action.name,
                    subtitle = action.description,
                    icon = Icons.Default.Send,
                    onClick = {
                        viewModel.selectedAction = action
                        onNext()
                    }
                )
            }
        }
    }
}

@Composable
fun WizardPreviewScreen(viewModel: WizardViewModel, onSaveSuccess: () -> Unit, onBack: () -> Unit) {
    WizardScaffold(title = "Workflow Preview", onBack = onBack) {
        Column(modifier = Modifier.padding(it).padding(24.dp)) {
            Text(viewModel.workflowName, style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            
            PreviewItem(label = "WHEN", text = viewModel.selectedTrigger?.name ?: "")
            Box(modifier = Modifier.padding(start = 20.dp).height(24.dp).width(2.dp).background(TextSecondary))
            PreviewItem(label = "THEN", text = viewModel.selectedAction?.name ?: "")
            
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                text = if (viewModel.isSaving) "Saving..." else "Save Automation",
                onClick = { viewModel.saveAutomation(onSaveSuccess) },
                enabled = !viewModel.isSaving
            )
        }
    }
}

@Composable
fun PreviewItem(label: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(40.dp).background(Surface, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Star, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Text(text, style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}