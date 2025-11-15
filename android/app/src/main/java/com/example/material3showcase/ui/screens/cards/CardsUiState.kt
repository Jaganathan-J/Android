package com.example.material3showcase.ui.screens.cards

import com.example.material3showcase.domain.model.CardModel

data class CardsUiState(
    val isLoading: Boolean = false,
    val cards: List<CardModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedId: String? = null
)

sealed class CardsEvent {
    data class OnCardClick(val id: String) : CardsEvent()
    object Retry : CardsEvent()
}
