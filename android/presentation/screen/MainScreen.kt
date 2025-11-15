package com.example.material3showcase.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.material3showcase.domain.model.ButtonConfig
import com.example.material3showcase.domain.model.ButtonStyle
import com.example.material3showcase.navigation.Screen
import com.example.material3showcase.presentation.viewmodel.MainUiState
import com.example.material3showcase.presentation.viewmodel.MainViewModel
import com.example.material3showcase.presentation.viewmodel.UiStatus
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GettingStartedScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Material 3 Showcase") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Getting Started",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Explore Material 3 components using the bottom navigation bar.")
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                StatusSection(uiState = uiState, onRetry = { viewModel.syncConfig() })
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text("Quick Links", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                QuickLinkCard(label = "Buttons", onClick = { navController.navigate(Screen.Buttons.route) })
                Spacer(modifier = Modifier.height(8.dp))
                QuickLinkCard(label = "Avatars", onClick = { navController.navigate(Screen.Avatars.route) })
                Spacer(modifier = Modifier.height(8.dp))
                QuickLinkCard(label = "Cards", onClick = { navController.navigate(Screen.Cards.route) })
            }

            if (uiState.buttonConfigs.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Sample Buttons from Config", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(uiState.buttonConfigs) { config ->
                    ConfiguredButtonPreview(config = config)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun StatusSection(uiState: MainUiState, onRetry: () -> Unit) {
    when (uiState.status) {
        UiStatus.Idle -> {
            Text("Config synced.")
        }
        UiStatus.Loading -> {
            Text("Syncing configuration...")
        }
        is UiStatus.Error -> {
            Column {
                Text("Failed to load config: ${(uiState.status as UiStatus.Error).message}")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = onRetry) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
private fun QuickLinkCard(label: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, fontWeight = FontWeight.SemiBold)
            Text("Tap to view $label components")
        }
    }
}

@Composable
private fun ConfiguredButtonPreview(config: ButtonConfig) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Button tapped") },
            text = { Text("You tapped '${config.label}'.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("OK") }
            }
        )
    }

    when (config.style) {
        ButtonStyle.FILLED -> Button(
            onClick = { showDialog = true },
            enabled = config.enabled,
            modifier = Modifier.fillMaxWidth()
        ) { Text(config.label) }

        ButtonStyle.OUTLINED -> OutlinedButton(
            onClick = { showDialog = true },
            enabled = config.enabled,
            modifier = Modifier.fillMaxWidth()
        ) { Text(config.label) }

        ButtonStyle.ELEVATED -> androidx.compose.material3.ElevatedButton(
            onClick = { showDialog = true },
            enabled = config.enabled,
            modifier = Modifier.fillMaxWidth()
        ) { Text(config.label) }

        ButtonStyle.TEXT -> TextButton(
            onClick = { showDialog = true },
            enabled = config.enabled,
            modifier = Modifier.fillMaxWidth()
        ) { Text(config.label) }

        ButtonStyle.TONAL -> FilledTonalButton(
            onClick = { showDialog = true },
            enabled = config.enabled,
            modifier = Modifier.fillMaxWidth()
        ) { Text(config.label) }
    }
}

// --- Additional component screens ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarsScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Avatars") }, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Avatar component examples go here.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(snackbarHostState: androidx.compose.material3.SnackbarHostState) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var lastClick by remember { mutableStateOf("") }

    LaunchedEffect(lastClick) {
        if (lastClick.isNotEmpty()) {
            snackbarHostState.showSnackbar("Clicked: $lastClick")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Buttons") }, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Filled")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { lastClick = "Filled" }, modifier = Modifier.fillMaxWidth()) {
                Text("Continue")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Outlined")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { lastClick = "Outlined" }, modifier = Modifier.fillMaxWidth()) {
                Text("Outlined")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Tonal")
            Spacer(modifier = Modifier.height(8.dp))
            FilledTonalButton(onClick = { lastClick = "Tonal" }, modifier = Modifier.fillMaxWidth()) {
                Text("Tonal")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Text")
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { lastClick = "Text" }, modifier = Modifier.fillMaxWidth()) {
                Text("Text Button")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Cards") }, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Card Title", fontWeight = FontWeight.Bold)
                    Text("Card description showcasing elevation and interaction.")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Dialogs") }, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Button(onClick = { showDialog = true }) {
                Text("Show Dialog")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Dialog Title") },
                    text = { Text("Dialog description with confirm and dismiss actions.") },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) { Text("Confirm") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) { Text("Dismiss") }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        delay(500)
        isError = text.length > 10
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Text Fields") }, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            androidx.compose.material3.OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Outlined") },
                isError = isError,
                supportingText = {
                    if (isError) Text("Max 10 characters", color = ErrorRed)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Filled") }
            )
        }
    }
}
