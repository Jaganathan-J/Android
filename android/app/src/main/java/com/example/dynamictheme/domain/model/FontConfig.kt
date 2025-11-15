package com.example.dynamictheme.domain.model

data class FontConfig(
    val family: String,
    val fallbacks: List<String> = emptyList(),
    val variants: List<String> = emptyList(),
    val axes: List<String> = emptyList()
)