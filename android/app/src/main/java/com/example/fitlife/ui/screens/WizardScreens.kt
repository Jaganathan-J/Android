package com.example.fitlife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.fitlife.domain.model.IntegrationItem
import com.example.fitlife.domain.repository.AutomationRepository
import com.example.fitlife.ui.components.AppToolbar
import com.example.fitlife.ui.components.CustomTextField
import com.example.fitlife.ui.components.PrimaryButton
import com.example.fitlife.ui.theme.BackgroundDark
import com.example.fitlife.ui.theme.SurfaceDark
import com.example.fitlife.ui.theme.TextGrey
import com.example.fitlife.ui.theme.TextWhite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Shared ViewModel for Wizard flow
@HiltViewModel
class WizardViewModel @Inject constructor(
    private val repository: AutomationRepository
) : ViewModel() {
    var automationName by mutableStateOf("")
    var selectedTrigger by mutableStateOf<IntegrationItem?>(null)
    var selectedAction by mutableStateOf<IntegrationItem?>(null)

    val triggers = repository.getTriggers()
    val actions = repository.getActions()

    fun save(onSuccess: () -> Unit) {
        if (selectedTrigger != null && selectedAction != null) {
            // Assuming coroutine scope launch in real implementation
            onSuccess()
        }
    }
}

@Composable
fun CreateAutomationScreen(navController: NavController, viewModel: WizardViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        AppToolbar(title = "Create Automation", onBack = { navController.popBackStack() })
        Column(modifier = Modifier.padding(24.dp).weight(1f)) {
            CustomTextField(
                value = viewModel.automationName,
                onValueChange = { viewModel.automationName = it },
                label = "Name your automation"
            )
        }
        PrimaryButton(
            text = "Continue",
            onClick = { navController.navigate("trigger_select") },
            modifier = Modifier.padding(24.dp),
            enabled = viewModel.automationName.isNotEmpty()
        )
    }
}

@Composable
fun TriggerSelectScreen(navController: NavController, viewModel: WizardViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        AppToolbar(title = "Choose a Trigger", onBack = { navController.popBackStack() })
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(viewModel.triggers) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(SurfaceDark, RoundedCornerShape(12.dp))
                        .clickable { 
                            viewModel.selectedTrigger = item
                            navController.navigate("action_select")
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(40.dp).background(Color.Gray, RoundedCornerShape(8.dp)))
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(item.name, color = TextWhite, style = MaterialTheme.typography.titleMedium)
                        item.description?.let { Text(it, color = TextGrey) }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionSelectScreen(navController: NavController, viewModel: WizardViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        AppToolbar(title = "Select an Action", onBack = { navController.popBackStack() })
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.actions) { item ->
                Column(
                    modifier = Modifier
                        .background(SurfaceDark, RoundedCornerShape(12.dp))
                        .clickable { 
                            viewModel.selectedAction = item
                            navController.navigate("preview")
                        }
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(Modifier.size(48.dp).background(Color.Gray, RoundedCornerShape(8.dp)))
                    Spacer(Modifier.height(16.dp))
                    Text(item.name, color = TextWhite)
                }
            }
        }
    }
}

@Composable
fun PreviewScreen(navController: NavController, viewModel: WizardViewModel) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        AppToolbar(title = "Summary", onBack = { navController.popBackStack() })
        Column(modifier = Modifier.padding(24.dp).weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(viewModel.automationName, style = MaterialTheme.typography.headlineLarge, color = TextWhite)
            Spacer(Modifier.height(32.dp))
            
            // Visualization
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     Box(Modifier.size(60.dp).background(SurfaceDark, RoundedCornerShape(12.dp)))
                     Text(viewModel.selectedTrigger?.name ?: "", color = TextGrey)
                }
                Text(" → ", color = TextWhite, style = MaterialTheme.typography.headlineLarge)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     Box(Modifier.size(60.dp).background(SurfaceDark, RoundedCornerShape(12.dp)))
                     Text(viewModel.selectedAction?.name ?: "", color = TextGrey)
                }
            }
        }
        PrimaryButton(
            text = "Save Automation",
            onClick = { 
                viewModel.save { 
                    navController.navigate("history") {
                        popUpTo("history") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.padding(24.dp)
        )
    }
}