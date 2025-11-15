package com.example.m3gallery.presentation.screens.cards

import com.example.m3gallery.domain.model.ComponentItem

data class CardsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val items: List<ComponentItem> = emptyList()
)
