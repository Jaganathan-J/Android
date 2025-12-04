package com.example.fitlifefinance.ui.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fitlifefinance.domain.model.BudgetCategory
import com.example.fitlifefinance.domain.repository.BudgetRepository
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.RedExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(repo: BudgetRepository) : ViewModel() {
    val categories = repo.getBudgetCategories().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

@Composable
fun BudgetScreen(navController: NavController, viewModel: BudgetViewModel = hiltViewModel()) {
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { navController.popBackStack() })
                Text("Budget Planner", style = MaterialTheme.typography.titleLarge)
                Text("Edit", color = GreenPrimary, fontWeight = FontWeight.Bold)
            }
        },
        bottomBar = { FitLifeBottomBar(Routes.BUDGET, { navController.navigate(it) }) }
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            // Month Selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ChevronLeft, null)
                Text("December 2024", modifier = Modifier.padding(horizontal = 16.dp), fontWeight = FontWeight.Bold)
                Icon(Icons.Default.ChevronRight, null)
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Summary Card
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                    Text("Monthly Budget", color = Color.Gray)
                    Text("$5,000.00", fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
                    LinearProgressIndicator(progress = { 0.63f }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)), color = GreenPrimary, trackColor = Color(0xFFE0E0E0))
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("$3,150 spent", color = GreenPrimary, fontSize = 12.sp)
                        Text("$1,850 left", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(categories) { cat ->
                    BudgetCategoryItem(cat)
                }
            }
        }
    }
}

@Composable
fun BudgetCategoryItem(cat: BudgetCategory) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(Color(cat.colorHex).copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                   // Placeholder icon
                   Box(modifier = Modifier.size(16.dp).background(Color(cat.colorHex), CircleShape))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(cat.name, fontWeight = FontWeight.SemiBold)
                    Text("$${cat.spent.toInt()} / $${cat.total.toInt()}", fontSize = 12.sp, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("${(cat.percent * 100).toInt()}%", color = if(cat.isOverBudget) RedExpense else Color(cat.colorHex), fontWeight = FontWeight.Bold)
                    if (cat.isOverBudget) {
                         Text("(Over budget!)", color = RedExpense, fontSize = 10.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { (cat.spent / cat.total).toFloat().coerceAtMost(1f) }, 
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)), 
                color = if(cat.isOverBudget) RedExpense else Color(cat.colorHex), 
                trackColor = Color(0xFFEEEEEE)
            )
        }
    }
}