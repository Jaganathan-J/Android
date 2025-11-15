package com.example.material3showcase.domain.model

// Represents a generic UI configuration fetched from backend
// In this sample we focus on button configuration as described in the TDD

data class ButtonConfig(
    val label: String,
    val style: ButtonStyle,
    val enabled: Boolean,
    val icon: String?
)

enum class ButtonStyle {
    FILLED,
    TEXT,
    OUTLINED,
    ELEVATED,
    TONAL;

    companion object {
        fun fromRaw(value: String): ButtonStyle = when (value.lowercase()) {
            "filled" -> FILLED
            "text" -> TEXT
            "outlined" -> OUTLINED
            "elevated" -> ELEVATED
            "tonal" -> TONAL
            else -> FILLED
        }
    }
}
