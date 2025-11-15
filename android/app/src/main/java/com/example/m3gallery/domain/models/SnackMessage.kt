package com.example.m3gallery.domain.models

data class SnackMessage(
    val id: String,
    val message: String,
    val actionLabel: String? = null
)
