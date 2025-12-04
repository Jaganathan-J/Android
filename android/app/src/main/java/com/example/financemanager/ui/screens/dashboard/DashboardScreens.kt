package com.example.financemanager.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
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
import com.example.financemanager.ui.theme.*
import com.example.financemanager.ui.components.GradientButton

@Composable
fun DashboardContainer(onCreateNew: () -> Unit, onLogout: () -> Unit) {
    var selectedTab = androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = SurfaceColor) {
                NavigationBarItem(
                    selected = selectedTab.intValue == 0,
                    onClick = { selectedTab.intValue = 0 },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    label = { Text("History") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryBlue, unselectedIconColor = TextGrey)
                )
                NavigationBarItem(
                    selected = selectedTab.intValue == 1,
                    onClick = { selectedTab.intValue = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryBlue, unselectedIconColor = TextGrey)
                )
            }
        },
        floatingActionButton = {
            if (selectedTab.intValue == 0) {
                FloatingActionButton(onClick = onCreateNew, containerColor = PrimaryBlue) {
                    Icon(Icons.Default.Add, null, tint = TextWhite)
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (selectedTab.intValue == 0) HistoryScreen() else ProfileScreen(onLogout)
        }
    }
}

@Composable
fun HistoryScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val history by viewModel.history.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(16.dp)) {
        Text("Execution History", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(history) { log ->
                Card(colors = CardDefaults.cardColors(containerColor = SurfaceColor)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(log.workflowName, color = TextWhite, fontWeight = FontWeight.Bold)
                            Text(log.message, color = TextGrey, fontSize = 12.sp)
                            Text(log.timestamp, color = TextGrey, fontSize = 10.sp)
                        }
                        StatusBadge(status = log.status.name)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(100.dp).background(SurfaceColor, CircleShape), contentAlignment = Alignment.Center) {
            Text("JD", color = PrimaryBlue, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        Text("John Doe", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("john@financemanager.com", color = TextGrey)
        
        Spacer(Modifier.weight(1f))
        GradientButton(text = "Log Out", onClick = onLogout)
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "SUCCESS" -> SuccessGreen
        "FAILED" -> ErrorRed
        else -> Color.Yellow
    }
    Text(
        text = status,
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .border(1.dp, color, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}