package com.example.fitlifefinance.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DonutLarge
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.fitlifefinance.ui.navigation.Routes
import com.example.fitlifefinance.ui.theme.GreenPrimary
import com.example.fitlifefinance.ui.theme.TextSecondary

@Composable
fun FitLifeBottomBar(currentRoute: String, onNavigate: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            Triple(Routes.HOME, "Home", Icons.Filled.Home to Icons.Outlined.Home),
            Triple(Routes.ANALYTICS, "Analytics", Icons.Filled.Analytics to Icons.Outlined.Analytics),
            Triple(Routes.ACCOUNTS, "Cards", Icons.Filled.CreditCard to Icons.Outlined.CreditCard),
            Triple(Routes.BUDGET, "Budget", Icons.Filled.DonutLarge to Icons.Outlined.DonutLarge),
            Triple(Routes.PROFILE, "Profile", Icons.Filled.AccountCircle to Icons.Outlined.AccountCircle)
        )

        items.forEach { (route, label, icons) ->
            val selected = currentRoute == route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(route) },
                icon = {
                    Icon(
                        imageVector = if (selected) icons.first else icons.second,
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenPrimary,
                    selectedTextColor = GreenPrimary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = Color.Transparent // Remove standard Material3 indicator pill for cleaner look if desired
                )
            )
        }
    }
}