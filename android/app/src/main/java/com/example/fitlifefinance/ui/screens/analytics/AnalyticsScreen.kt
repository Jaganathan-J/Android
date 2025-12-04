package com.example.fitlifefinance.ui.screens.analytics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fitlifefinance.domain.repository.UserRepository
import com.example.fitlifefinance.ui.components.FitLifeBottomBar
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.RedExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    val stats = userRepository.getWeeklyStats().stateIn(viewModelScope, SharingStarted.Lazily, null)
}

@Composable
fun AnalyticsScreen(navController: NavController, viewModel: AnalyticsViewModel = hiltViewModel()) {
    val stats by viewModel.stats.collectAsState()

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Analytics", style = MaterialTheme.typography.headlineLarge)
                TextButton(onClick = {}) { Text("Export", color = GreenPrimary) }
            }
        },
        bottomBar = { FitLifeBottomBar(Routes.ANALYTICS, { navController.navigate(it) }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Segment Control Mock
            Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFEEEEEE), RoundedCornerShape(8.dp)).padding(4.dp)) {
                listOf("Week", "Month", "Quarter", "Year").forEachIndexed { index, title ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(if (index == 0) GreenPrimary else Color.Transparent, RoundedCornerShape(6.dp))
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(title, color = if (index == 0) Color.White else Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Bar Chart
            Text("Spending This Week", fontWeight = FontWeight.Bold)
            Text("Dec 1-7", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth().height(150.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val values = stats?.spendingByDay ?: listOf(0.1, 0.3, 0.2, 0.9, 0.1, 0.4, 0.1)
                values.forEachIndexed { i, v ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .fillMaxHeight((v / 500.0).toFloat().coerceIn(0.1f, 1f))
                                .background(
                                    when (i) {
                                        3 -> Color(0xFFFD7E14)
                                        5 -> RedExpense
                                        else -> GreenPrimary
                                    }, 
                                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                        )
                        Text(days[i], fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AnalyticsCard("Total Spent", "$892.50", RedExpense, Modifier.weight(1f))
                AnalyticsCard("Total Income", "$4,500", GreenPrimary, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Spending by Category", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Donut Chart
                val breakdown = stats?.categoryBreakdown ?: emptyList()
                Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(120.dp)) {
                        var startAngle = -90f
                        breakdown.forEach { cat ->
                            val sweep = cat.percent * 360f
                            drawArc(Color(cat.colorHex), startAngle, sweep, false, style = Stroke(30f, cap = StrokeCap.Butt))
                            startAngle += sweep
                        }
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column {
                    breakdown.forEach { cat ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                            Box(modifier = Modifier.size(8.dp).background(Color(cat.colorHex), CircleShape))
                            Text(cat.name, modifier = Modifier.padding(start = 8.dp), fontSize = 14.sp)
                            Text("${(cat.percent * 100).toInt()}%", modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyticsCard(title: String, amount: String, color: Color, modifier: Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp), modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(amount, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}