package com.example.material3showcase.ui.screens.avatars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.usecase.GetAvatarsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AvatarsViewModel @Inject constructor(
    private val getAvatarsConfig: GetAvatarsConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AvatarsUiState(isLoading = true))
    val uiState: StateFlow<AvatarsUiState> = _uiState.asStateFlow()

    init {
        loadAvatars()
    }

    fun onEvent(event: AvatarsEvent) {
        when (event) {
            is AvatarsEvent.OnAvatarClick -> {
                _uiState.update { it.copy(selectedId = event.id) }
            }
            AvatarsEvent.Retry -> loadAvatars()
        }
    }

    private fun loadAvatars() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getAvatarsConfig().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.update { it.copy(isLoading = false, avatars = list) }
                    },
                    onFailure = { throwable ->
                        _uiState.update { it.copy(isLoading = false, errorMessage = throwable.message) }
                    }
                )
            }
        }
    }
}
