package com.automationoverflow.app.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Dark Mode Base Colors
val AppBackground = Color(0xFF0A0A14) // Deep Navy/Black
val AppSurface = Color(0xFF141423)    // Dark Blue Gray
val AppOnSurface = Color(0xFFFFFFFF)
val AppSecondaryText = Color(0xFF8A8A99)

// Gradient Colors
val Violet700 = Color(0xFF7F00FF)
val Cyan400 = Color(0xFF00D4FF)

val PrimaryGradient = Brush.horizontalGradient(
    colors = listOf(Violet700, Cyan400)
)

// Semantic
val SuccessGreen = Color(0xFF4CAF50)
val ErrorRed = Color(0xFFCF6679)
val RunningYellow = Color(0xFFFFC107)