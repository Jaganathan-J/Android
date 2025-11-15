package com.example.m3gallery.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "M3 Gallery") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO drawer */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigate(Screen.Settings.route) }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Featured Components",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )

            ListItem(
                headlineContent = { Text("Buttons") },
                supportingContent = { Text("Filled, elevated, outlined and text buttons with loading states.") },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 4.dp),
                tonalElevation = 2.dp,
                shadowElevation = 1.dp,
                overlineContent = null,
                leadingContent = null,
                trailingContent = {
                    IconButton(onClick = { onNavigate(Screen.Buttons.route) }) {
                        Text(text = ">")
                    }
                }
            )
            Divider()

            ListItem(
                headlineContent = { Text("Cards") },
                supportingContent = { Text("Elevated and filled cards with icons and text.") },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp),
                trailingContent = {
                    IconButton(onClick = { onNavigate(Screen.Cards.route) }) {
                        Text(text = ">")
                    }
                }
            )
            Divider()

            ListItem(
                headlineContent = { Text("Dialogs") },
                supportingContent = { Text("Confirmation, warning and input dialogs.") },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp),
                trailingContent = {
                    IconButton(onClick = { onNavigate(Screen.Dialogs.route) }) {
                        Text(text = ">")
                    }
                }
            )
        }
    }
}
