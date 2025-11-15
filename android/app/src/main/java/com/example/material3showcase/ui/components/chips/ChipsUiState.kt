package com.example.material3showcase.ui.components.chips

import com.example.material3showcase.domain.model.ChipModel

data class ChipsUiState(
    val selectedChip: String = "",
    val filterChips: List<ChipModel> = emptyList()
)
