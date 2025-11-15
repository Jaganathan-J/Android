package com.example.material3showcase.domain.model

data class ChipModel(
    val id: String,
    val label: String,
    val selected: Boolean = false,
    val enabled: Boolean = true
)
