package com.automationoverflow.app.presentation.wizard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.* // For generic icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.automationoverflow.app.presentation.components.GradientButton
import com.automationoverflow.app.presentation.components.SelectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameScreen(
    viewModel: WizardViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val name by viewModel.name.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Automation") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Column(Modifier.padding(it).padding(24.dp).fillMaxSize()) {
            Text("Name your skill", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.setName(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("e.g. Daily Summary") }
            )
            Spacer(Modifier.weight(1f))
            GradientButton(
                text = "Continue",
                onClick = onNext,
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerSelectScreen(
    viewModel: WizardViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val triggers by viewModel.availableTriggers.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose a Trigger") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        LazyColumn(Modifier.padding(it).padding(16.dp)) {
            items(triggers) { trigger ->
                SelectionCard(
                    icon = if(trigger.iconType == "email") Icons.Default.Email else Icons.Default.TouchApp,
                    title = trigger.name,
                    subtitle = trigger.description,
                    onClick = {
                        viewModel.selectTrigger(trigger)
                        onNext()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionSelectScreen(
    viewModel: WizardViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val actions by viewModel.availableActions.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select an Action") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.padding(it).padding(16.dp)) {
                items(actions) { action ->
                    SelectionCard(
                        icon = if(action.iconType == "slack") Icons.Default.Code else Icons.Default.EditCalendar,
                        title = action.name,
                        subtitle = action.description,
                        onClick = {
                            viewModel.selectAction(action)
                            onNext()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    viewModel: WizardViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val trigger by viewModel.selectedTrigger.collectAsStateWithLifecycle()
    val action by viewModel.selectedAction.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workflow Preview") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Column(Modifier.padding(it).padding(24.dp).fillMaxSize()) {
            Text("Name: $name", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))
            
            // Summary Items
            Text("When this happens...", color = Color.Gray)
            trigger?.let {
                SelectionCard(Icons.Default.Email, it.name, it.description) {}
            }
            
            Spacer(Modifier.height(8.dp))
            Icon(Icons.Default.ArrowDownward, null, tint = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(8.dp))
            
            Text("Do this...", color = Color.Gray)
            action?.let {
                SelectionCard(Icons.Default.Code, it.name, it.description) {}
            }
            
            Spacer(Modifier.weight(1f))
            GradientButton(
                text = "Save Automation",
                onClick = {
                    viewModel.saveAutomation(onSuccess = onSave)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}