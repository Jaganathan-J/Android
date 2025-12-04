package com.example.fitlifefinance.ui.screens.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fitlifefinance.domain.model.Account
import com.example.fitlifefinance.domain.repository.AccountRepository
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.RedExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(repo: AccountRepository) : ViewModel() {
    val accounts = repo.getAccounts().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val total = repo.getTotalBalance().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
}

@Composable
fun AccountsScreen(navController: NavController, viewModel: AccountsViewModel = hiltViewModel()) {
    val accounts by viewModel.accounts.collectAsState()
    val total by viewModel.total.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Bank Accounts", fontWeight = FontWeight.Bold) },
                navigationIcon = { Icon(Icons.Default.ArrowBack, "Back", Modifier.padding(start = 16.dp)) },
                actions = { Icon(Icons.Default.Add, "Add", tint = GreenPrimary, modifier = Modifier.padding(end = 16.dp)) }
            )
        },
        bottomBar = { FitLifeBottomBar(Routes.ACCOUNTS, { navController.navigate(it) }) }
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            // Total Card
            Card(colors = CardDefaults.cardColors(containerColor = GreenPrimary), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Total Balance (All Accounts)", color = Color.White)
                    Text("$$total", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Linked Accounts (${accounts.size})", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(accounts) { acc -> AccountCard(acc) }
                item {
                     OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(50.dp), border = androidx.compose.foundation.BorderStroke(1.dp, GreenPrimary)) {
                         Text("+ Link New Account", color = GreenPrimary)
                     }
                }
            }
        }
    }
}

@Composable
fun AccountCard(acc: Account) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(Color.LightGray.copy(alpha=0.3f), RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${acc.bankName} ${acc.accountType}", fontWeight = FontWeight.Bold)
                Text("****${acc.mask} • ${acc.status.name}", color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if(acc.balance < 0) "-$${kotlin.math.abs(acc.balance)}" else "$${acc.balance}",
                    fontWeight = FontWeight.Bold,
                    color = if(acc.balance < 0) RedExpense else Color.Black
                )
            }
        }
    }
}