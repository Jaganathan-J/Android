package com.example.material3showcase.domain.model

data class CardModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val outlined: Boolean = false
)
