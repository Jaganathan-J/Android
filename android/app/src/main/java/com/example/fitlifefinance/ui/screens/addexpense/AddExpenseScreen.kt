package com.example.fitlifefinance.ui.screens.addexpense

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitlifefinance.ui.theme.GreenPrimary

@Composable
fun AddExpenseScreen(navController: NavController) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.Close, "Close") } },
                title = { Text("Add Expense", fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Save Expense", fontSize = 18.sp)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(24.dp)) {
            Text("$0.00", fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(value = "Food & Dining", onValueChange = {}, label = { Text("Category") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = "Chase Checking", onValueChange = {}, label = { Text("Pay From") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Notes (Optional)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Make this recurring", modifier = Modifier.weight(1f))
                Switch(checked = false, onCheckedChange = {})
            }
        }
    }
}