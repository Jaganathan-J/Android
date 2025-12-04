package com.example.fitlifefinance.ui.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fitlifefinance.domain.repository.TransactionRepository
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.components.TransactionItem
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TxViewModel @Inject constructor(repo: TransactionRepository) : ViewModel() {
    val txs = repo.getAllTransactions().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

@Composable
fun TransactionsScreen(navController: NavController, viewModel: TxViewModel = hiltViewModel()) {
    val list by viewModel.txs.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Transactions", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = { IconButton(onClick={}) { Icon(Icons.Default.FilterList, "Filter") } }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(horizontal = 16.dp)) {
            TextField(
                value = "", onValueChange = {},
                placeholder = { Text("Search transactions...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xFFF5F5F5), focusedContainerColor = Color(0xFFF5F5F5), unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("All", color = Color.White) }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = GreenPrimary))
                FilterChip(selected = false, onClick = {}, label = { Text("Income") })
                FilterChip(selected = false, onClick = {}, label = { Text("Expense") })
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                item { Text("Today", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp)) }
                items(list) { tx -> TransactionItem(tx) }
            }
        }
    }
}