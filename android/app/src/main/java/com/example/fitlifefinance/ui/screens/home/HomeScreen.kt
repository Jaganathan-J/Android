package com.example.fitlifefinance.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.ce
import com.example.fitlifefinance.domain.model.Transaction
import com.example.fitlifefinance.domain.model.TransactionType
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.components.TransactionItem
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.GreenDark
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToNotifications: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToAddExpense: () -> Unit
) {
    val user by viewModel.user.collectAsState(initial = null)
    val totalBalance by viewModel.totalBalance.collectAsState(initial = 0.0)
    val recentTransactions by viewModel.recentTransactions.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            FitLifeBottomBar(currentRoute = Routes.HOME, onNavigate = { navController.navigate(it) })
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            // Header Layer (Green Background)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(GreenPrimary)
                    .padding(24.dp)
            ) {
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text("Good Morning,", color = Color.White.copy(alpha = 0.8f))
                            Text(user?.name ?: "User", color = Color.White, style = MaterialTheme.typography.titleLarge)
                        }
                        BadgedBox(badge = { Badge { Text("3") } }) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.clickable { onNavigateToNotifications() }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Balance Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = GreenDark),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Total Balance", color = Color.White.copy(alpha = 0.8f))
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.Visibility, "Show/Hide", tint = Color.White.copy(alpha = 0.6f), modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "$24,562.80", // Hardcoded for visuals matching wireframe
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.ArrowUpward, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Text(" +12.5% ", color = Color.White, fontWeight = FontWeight.Bold)
                                Text("from last month", color = Color.White.copy(alpha = 0.8f))
                            }
                        }
                    }
                }
            }
            
            // Content Layer (Overlapping white container effect)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text("Quick Actions", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickActionButton("Send", Icons.Default.Send, Color(0xFFE3F2FD), Color(0xFF1E88E5), {})
                    QuickActionButton("Request", Icons.Default.RequestQuote, Color(0xFFFFF3E0), Color(0xFFFB8C00), {})
                    QuickActionButton("Add", Icons.Default.Add, Color(0xFFF3E5F5), Color(0xFF8E24AA), onNavigateToAddExpense)
                    QuickActionButton("More", Icons.Default.MoreHoriz, Color(0xFFE0F2F1), Color(0xFF00897B), {})
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Recent Transactions", style = MaterialTheme.typography.titleMedium)
                    Text("See All", color = GreenPrimary, modifier = Modifier.clickable { onNavigateToTransactions() })
                }
                
                LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                    items(recentTransactions) { tx ->
                        TransactionItem(tx)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(label: String, icon: ImageVector, bgColor: Color, iconColor: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = iconColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}