package com.example.fitlife.presentation.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.* 
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.ActionOption
import com.example.fitlife.domain.model.Trigger
import com.example.fitlife.domain.repository.AutomationRepository
import com.example.fitlife.presentation.components.FitLifeTextField
import com.example.fitlife.presentation.components.GradientButton
import com.example.fitlife.presentation.components.SimpleTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// --- ViewModels ---

@HiltViewModel
class CreateViewModel @Inject constructor(private val repo: AutomationRepository) : ViewModel() {
    var triggers by mutableStateOf<List<Trigger>>(emptyList())
    var actions by mutableStateOf<List<ActionOption>>(emptyList())
    var isSaving by mutableStateOf(false)

    init {
        viewModelScope.launch {
            triggers = repo.getTriggers()
            actions = repo.getActions()
        }
    }

    fun save(name: String, tId: String, aId: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            isSaving = true
            repo.createWorkflow(name, tId, aId)
            isSaving = false
            onComplete()
        }
    }
    
    fun getTrigger(id: String) = triggers.find { it.id == id }
    fun getAction(id: String) = actions.find { it.id == id }
}

// --- Screens ---

@Composable
fun CreateNameScreen(onBack: () -> Unit, onContinue: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    Scaffold(
        topBar = { SimpleTopBar("Create Automation", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        Column(modifier = Modifier.padding(it).padding(24.dp)) {
            Text("Name your automation", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            FitLifeTextField(value = name, onValueChange = { name = it }, placeholder = "e.g. Morning Routine")
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(text = "Continue", onClick = { if(name.isNotBlank()) onContinue(name) }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ChooseTriggerScreen(
    onBack: () -> Unit, 
    onTriggerSelected: (String) -> Unit,
    viewModel: CreateViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { SimpleTopBar("Choose Timer", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text("When this happens...", color = Color.Gray, fontSize = 16.sp) }
            items(viewModel.triggers) { t ->
                 SelectionCard(title = t.title, subtitle = t.subtitle, onClick = { onTriggerSelected(t.id) })
            }
        }
    }
}

@Composable
fun ChooseActionScreen(
    onBack: () -> Unit,
    onActionSelected: (String) -> Unit,
    viewModel: CreateViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { SimpleTopBar("Choose Action", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text("Do this...", color = Color.Gray, fontSize = 16.sp) }
            items(viewModel.actions) { a ->
                 SelectionCard(title = a.title, subtitle = a.subtitle, onClick = { onActionSelected(a.id) })
            }
        }
    }
}

@Composable
fun PreviewScreen(
    workflowName: String,
    triggerId: String,
    actionId: String,
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: CreateViewModel = hiltViewModel()
) {
    val trigger = viewModel.getTrigger(triggerId)
    val action = viewModel.getAction(actionId)

    Scaffold(
        topBar = { SimpleTopBar("Review", onBack) },
        containerColor = Color(0xFF0A0A16)
    ) {
        Column(modifier = Modifier.padding(it).padding(24.dp)) {
            Text(workflowName, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            if (trigger != null && action != null) {
                SelectionCard(title = trigger.title, subtitle = "Trigger", onClick = {})
                Box(modifier = Modifier.height(32.dp).padding(start = 24.dp).width(2.dp).background(Color.Gray))
                SelectionCard(title = action.title, subtitle = "Action", onClick = {})
            } else {
                CircularProgressIndicator()
            }
            
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                text = if(viewModel.isSaving) "Saving..." else "Save Automation",
                onClick = { viewModel.save(workflowName, triggerId, actionId, onSave) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isSaving
            )
        }
    }
}

@Composable
fun SelectionCard(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C2D), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder Icon
        Box(modifier = Modifier.size(40.dp).background(Color(0xFF3B82F6), RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Color.Gray, fontSize = 14.sp)
        }
    }
}