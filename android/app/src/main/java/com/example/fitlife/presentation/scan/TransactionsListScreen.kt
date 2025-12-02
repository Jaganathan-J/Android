package com.example.fitlife.presentation.scan

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlife.presentation.dashboard.DashboardViewModel
import com.example.fitlife.presentation.dashboard.TransactionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsListScreen(
    onNavigateBack: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel() // Reusing for simplicity
) {
    val transactions by viewModel.recentTransactions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it).padding(16.dp)
        ) {
            items(transactions) { t ->
                TransactionItem(t)
                Divider()
            }
        }
    }
}