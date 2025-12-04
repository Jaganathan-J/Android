package com.example.autocompleteworkflow.presentation.screens.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autocompleteworkflow.domain.model.Integration
import com.example.autocompleteworkflow.domain.repository.WorkflowRepository
import com.example.autocompleteworkflow.presentation.components.PrimaryButton
import com.example.autocompleteworkflow.presentation.components.StandardTopBar
import com.example.autocompleteworkflow.presentation.theme.BackgroundDark
import com.example.autocompleteworkflow.presentation.theme.SurfaceDark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Steps in the Wizard
enum class CreateStep { NAME, TRIGGER, ACTION, PREVIEW }

@HiltViewModel
class CreateWorkflowViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {

    private val _step = MutableStateFlow(CreateStep.NAME)
    val step = _step.asStateFlow()

    var workflowName by mutableStateOf("")
    var selectedTrigger by mutableStateOf<Integration?>(null)
    var selectedAction by mutableStateOf<Integration?>(null)
    
    private val _triggers = MutableStateFlow<List<Integration>>(emptyList())
    val triggers = _triggers.asStateFlow()
    
    private val _actions = MutableStateFlow<List<Integration>>(emptyList())
    val actions = _actions.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTriggers().collect { _triggers.value = it }
        }
    }

    fun fetchActions() {
        selectedTrigger?.let {
           viewModelScope.launch {
               repository.getActions(it.id).collect { list ->
                   _actions.value = list
               }
           }
        }
    }

    fun nextStep() {
        when (_step.value) {
            CreateStep.NAME -> _step.value = CreateStep.TRIGGER
            CreateStep.TRIGGER -> { fetchActions(); _step.value = CreateStep.ACTION }
            CreateStep.ACTION -> _step.value = CreateStep.PREVIEW
            CreateStep.PREVIEW -> { /* Finish handled by save */ }
        }
    }

    fun prevStep(): Boolean {
        return when (_step.value) {
            CreateStep.NAME -> true // Should exit flow
            CreateStep.TRIGGER -> { _step.value = CreateStep.NAME; false }
            CreateStep.ACTION -> { _step.value = CreateStep.TRIGGER; false }
            CreateStep.PREVIEW -> { _step.value = CreateStep.ACTION; false }
        }
    }

    fun saveWorkflow(onSuccess: () -> Unit) {
        if (selectedTrigger != null && selectedAction != null) {
            viewModelScope.launch {
                repository.createWorkflow(workflowName, selectedTrigger!!, selectedAction!!)
                onSuccess()
            }
        }
    }
}

@Composable
fun CreateWorkflowFlow(
    onFinished: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateWorkflowViewModel = hiltViewModel()
) {
    val step by viewModel.step.collectAsState()
    
    BackHandler {
        if (viewModel.prevStep()) onBack()
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            StandardTopBar(
                title = when(step) {
                    CreateStep.NAME -> "Initiate Automation"
                    CreateStep.TRIGGER -> "Choose a Trigger"
                    CreateStep.ACTION -> "Select an Action"
                    CreateStep.PREVIEW -> "Workflow Preview"
                },
                onBack = { if (viewModel.prevStep()) onBack() }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            when (step) {
                CreateStep.NAME -> StepName(viewModel)
                CreateStep.TRIGGER -> StepTrigger(viewModel)
                CreateStep.ACTION -> StepAction(viewModel)
                CreateStep.PREVIEW -> StepPreview(viewModel, onFinished)
            }
        }
    }
}

@Composable
fun StepName(viewModel: CreateWorkflowViewModel) {
    Column(modifier = Modifier.padding(24.dp).fillMaxSize()) {
        Text("Name your automation", color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.workflowName,
            onValueChange = { viewModel.workflowName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(text = "Continue", onClick = { viewModel.nextStep() }, enabled = viewModel.workflowName.isNotEmpty())
    }
}

@Composable
fun StepTrigger(viewModel: CreateWorkflowViewModel) {
    val triggers by viewModel.triggers.collectAsState()
    IntegrationList(triggers, viewModel.selectedTrigger) {
        viewModel.selectedTrigger = it
        viewModel.nextStep()
    }
}

@Composable
fun StepAction(viewModel: CreateWorkflowViewModel) {
    val actions by viewModel.actions.collectAsState()
    IntegrationList(actions, viewModel.selectedAction) {
        viewModel.selectedAction = it
        viewModel.nextStep()
    }
}

@Composable
fun IntegrationList(
    items: List<Integration>,
    selected: Integration?,
    onSelect: (Integration) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items) { item ->
            Card(
                onClick = { onSelect(item) },
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    // Icon placeholder
                    Box(modifier = Modifier.size(40.dp).background(Color.Gray.copy(0.2f), CircleShape))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(item.name, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(item.description, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun StepPreview(viewModel: CreateWorkflowViewModel, onFinished: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp).fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        
        PreviewCard(viewModel.selectedTrigger, isTrigger = true)
        
        // Connector line logic visual
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).height(40.dp).width(2.dp).background(Color.Gray))
        
        PreviewCard(viewModel.selectedAction, isTrigger = false)
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(text = "Save Automation", onClick = { viewModel.saveWorkflow(onFinished) })
    }
}

@Composable
fun PreviewCard(integration: Integration?, isTrigger: Boolean) {
    if (integration == null) return
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
             Box(modifier = Modifier.size(48.dp).background(Color.Gray.copy(0.2f), CircleShape), contentAlignment = Alignment.Center) {
                 Text(if(isTrigger) "T" else "A", color = Color.White)
             }
             Spacer(modifier = Modifier.width(16.dp))
             Column {
                 Text(if(isTrigger) "Trigger" else "Action", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                 Text(integration.name, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
             }
        }
    }
}