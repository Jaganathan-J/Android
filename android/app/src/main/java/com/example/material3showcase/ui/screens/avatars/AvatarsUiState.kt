package com.example.material3showcase.ui.screens.avatars

import com.example.material3showcase.domain.model.AvatarModel

data class AvatarsUiState(
    val isLoading: Boolean = false,
    val avatars: List<AvatarModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedId: String? = null
)

sealed class AvatarsEvent {
    data class OnAvatarClick(val id: String) : AvatarsEvent()
    object Retry : AvatarsEvent()
}
