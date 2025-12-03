package com.fitlife.automation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitlife.automation.domain.model.*
import com.fitlife.automation.domain.repository.AuthRepository
import com.fitlife.automation.domain.repository.WorkflowRepository
import com.fitlife.automation.ui.components.*
import com.fitlife.automation.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.* 
import kotlinx.coroutines.launch
import javax.inject.Inject

// --- LOGIN ---

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepo: AuthRepository) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    val loginEvent = MutableSharedFlow<Boolean>()

    fun login() {
        viewModelScope.launch {
            isLoading = true
            val result = authRepo.login(email, password)
            isLoading = false
            if (result.isSuccess) loginEvent.emit(true)
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        viewModel.loginEvent.collect { navController.navigate("dashboard") { popUpTo("login") { inclusive = true } } }
    }
    
    Column(
        modifier = Modifier.fillMaxSize().background(MidnightBlue).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log In", style = MaterialTheme.typography.headlineLarge, color = TextWhite)
        Spacer(Modifier.height(48.dp))
        FitLifeTextField(viewModel.email, { viewModel.email = it }, "Email Address")
        Spacer(Modifier.height(16.dp))
        FitLifeTextField(viewModel.password, { viewModel.password = it }, "Password", visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(32.dp))
        PrimaryButton("Log In", { viewModel.login() }, isLoading = viewModel.isLoading)
    }
}

// --- DASHBOARD / HISTORY ---

@HiltViewModel
class DashboardViewModel @Inject constructor(repo: WorkflowRepository) : ViewModel() {
    val history = repo.getHistory().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        containerColor = MidnightBlue,
        topBar = {
            TopAppBar(
                title = { Text("Execution History", color = TextWhite) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MidnightBlue),
                actions = {
                     IconButton(onClick = { navController.navigate("profile") }) {
                         Box(Modifier.size(32.dp).background(NeonPurple, CircleShape))
                     }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("create_init") }, containerColor = NeonPurple) {
                Text("+", color = TextWhite, style = MaterialTheme.typography.headlineLarge)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp)) {
            items(history) { item ->
                HistoryItemCard(item)
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: Workflow) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium, color = TextWhite)
                Text("${item.triggerName} -> ${item.actionName}", style = MaterialTheme.typography.labelMedium, color = TextGrey)
                Text(item.lastRun, style = MaterialTheme.typography.labelSmall, color = TextGrey)
            }
            Badge(containerColor = if (item.status == "Success") SuccessGreen else ErrorRed) {
                Text(item.status, modifier = Modifier.padding(4.dp), color = TextWhite)
            }
        }
    }
}

// --- WIZARD ---

@HiltViewModel
class WorkflowWizardViewModel @Inject constructor(private val repo: WorkflowRepository) : ViewModel() {
    var name by mutableStateOf("")
    var selectedTrigger: Trigger? by mutableStateOf(null)
    var selectedAction: Action? by mutableStateOf(null)
    
    var triggers = mutableStateOf<List<Trigger>>(emptyList())
    var actions = mutableStateOf<List<Action>>(emptyList())
    var isSaving by mutableStateOf(false)

    init {
        viewModelScope.launch {
            triggers.value = repo.getTriggers()
            actions.value = repo.getActions()
        }
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isSaving = true
            repo.createWorkflow(name, selectedTrigger?.id ?: "", selectedAction?.id ?: "")
            isSaving = false
            onSuccess()
        }
    }
}

@Composable
fun InitiateScreen(navController: NavController, viewModel: WorkflowWizardViewModel) {
    Column(Modifier.fillMaxSize().background(MidnightBlue).padding(24.dp)) {
        Text("Initiate Automation", style = MaterialTheme.typography.headlineLarge, color = TextWhite)
        Spacer(Modifier.height(32.dp))
        FitLifeTextField(viewModel.name, { viewModel.name = it }, "Workflow Name")
        Spacer(Modifier.weight(1f))
        PrimaryButton("Continue", { navController.navigate("create_trigger") }, enabled = viewModel.name.isNotEmpty())
    }
}

@Composable
fun TriggerSelectScreen(navController: NavController, viewModel: WorkflowWizardViewModel) {
    val list by viewModel.triggers
    Column(Modifier.fillMaxSize().background(MidnightBlue).padding(16.dp)) {
        Text("Choose a Trigger", style = MaterialTheme.typography.headlineMedium, color = TextWhite)
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(list) { trigger ->
                SelectionCard(
                    trigger.name, trigger.serviceName, trigger.iconVector, trigger.color, 
                    isSelected = viewModel.selectedTrigger == trigger
                ) {
                    viewModel.selectedTrigger = trigger
                    navController.navigate("create_action")
                }
            }
        }
    }
}

@Composable
fun ActionSelectScreen(navController: NavController, viewModel: WorkflowWizardViewModel) {
    val list by viewModel.actions
    Column(Modifier.fillMaxSize().background(MidnightBlue).padding(16.dp)) {
        Text("Select an Action", style = MaterialTheme.typography.headlineMedium, color = TextWhite)
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(list) { action ->
                SelectionCard(
                    action.name, action.serviceName, action.iconVector, action.color,
                    isSelected = viewModel.selectedAction == action
                ) {
                    viewModel.selectedAction = action
                    navController.navigate("create_preview")
                }
            }
        }
    }
}

@Composable
fun PreviewScreen(navController: NavController, viewModel: WorkflowWizardViewModel) {
    Column(Modifier.fillMaxSize().background(MidnightBlue).padding(24.dp)) {
        Text("Preview Workflow", style = MaterialTheme.typography.headlineLarge, color = TextWhite)
        Spacer(Modifier.height(32.dp))
        
        Text("If...", color = NeonPurple, style = MaterialTheme.typography.titleMedium)
        SelectionCard(viewModel.selectedTrigger?.name ?: "", viewModel.selectedTrigger?.serviceName ?: "", viewModel.selectedTrigger!!.iconVector, viewModel.selectedTrigger!!.color) {} 
        
        Box(Modifier.height(40.dp).padding(start=24.dp).width(2.dp).background(TextGrey))
        
        Text("Then...", color = NeonPink, style = MaterialTheme.typography.titleMedium)
        SelectionCard(viewModel.selectedAction?.name ?: "", viewModel.selectedAction?.serviceName ?: "", viewModel.selectedAction!!.iconVector, viewModel.selectedAction!!.color) {} 

        Spacer(Modifier.weight(1f))
        PrimaryButton("Save Automation", { 
            viewModel.save { 
                navController.navigate("dashboard") { popUpTo("dashboard") { inclusive = true } }
            }
        }, isLoading = viewModel.isSaving)
    }
}

// --- PROFILE ---
@Composable
fun ProfileScreen(navController: NavController, authRepo: AuthRepository) {
    // Simplified mock user
    Column(Modifier.fillMaxSize().background(MidnightBlue).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(100.dp).background(NeonPurple, CircleShape))
        Spacer(Modifier.height(16.dp))
        Text("Alex", style = MaterialTheme.typography.headlineMedium, color = TextWhite)
        Text("alex@example.com", style = MaterialTheme.typography.bodyLarge, color = TextGrey)
        Spacer(Modifier.height(48.dp))
        
        Button(
            onClick = { 
                // Logout 
                navController.navigate("login") { popUpTo(0) }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkSurface)
        ) {
             Icon(Icons.AutoMirrored.Filled.Logout, null, tint = ErrorRed)
             Spacer(Modifier.width(8.dp))
             Text("Log Out", color = ErrorRed)
        }
    }
}