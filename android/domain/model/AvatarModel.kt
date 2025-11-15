package com.example.material3showcase.domain.model

data class AvatarModel(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val hasBadge: Boolean = false
)
