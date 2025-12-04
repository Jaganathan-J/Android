package com.example.fitlifefinance.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitlifefinance.domain.model.IconType
import com.example.fitlifefinance.domain.model.Transaction
import com.example.fitlifefinance.domain.model.TransactionType
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.RedExpense

@Composable
fun TransactionItem(tx: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFF1F1F1), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        val (icon, tint, bg) = when (tx.iconType) {
            IconType.FOOD -> Triple(Icons.Default.LocalDining, Color(0xFFE91E63), Color(0xFFFCE4EC))
            IconType.SHOPPING -> Triple(Icons.Default.ShoppingBag, Color(0xFF9C27B0), Color(0xFFF3E5F5))
            IconType.BILLS -> Triple(Icons.Default.ElectricBolt, Color(0xFF2196F3), Color(0xFFE3F2FD))
            IconType.SALARY -> Triple(Icons.Default.MonetizationOn, GreenPrimary, Color(0xFFE8F5E9))
            IconType.COFFEE -> Triple(Icons.Default.LocalCafe, Color(0xFF795548), Color(0xFFEFEBE9))
            else -> Triple(Icons.Default.MonetizationOn, Color.Gray, Color.LightGray)
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = tint)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(tx.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(tx.subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Text(
            text = (if (tx.type == TransactionType.INCOME) "+" else "") + String.format("$%.2f", tx.amount),
            color = if (tx.type == TransactionType.INCOME) GreenPrimary else RedExpense,
            fontWeight = FontWeight.Bold
        )
    }
}