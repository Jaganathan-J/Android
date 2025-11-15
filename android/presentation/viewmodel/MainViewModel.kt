package com.example.material3showcase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.model.ButtonConfig
import com.example.material3showcase.domain.usecase.SyncConfigUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiStatus {
    data object Idle : UiStatus
    data object Loading : UiStatus
    data class Error(val message: String) : UiStatus
}

data class MainUiState(
    val status: UiStatus = UiStatus.Idle,
    val buttonConfigs: List<ButtonConfig> = emptyList()
)

class MainViewModel @Inject constructor(
    private val syncConfigUseCase: SyncConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        syncConfig()
    }

    fun syncConfig() {
        _uiState.value = _uiState.value.copy(status = UiStatus.Loading)
        viewModelScope.launch {
            val result = syncConfigUseCase()
            result
                .onSuccess { configs ->
                    _uiState.value = MainUiState(
                        status = UiStatus.Idle,
                        buttonConfigs = configs
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        status = UiStatus.Error(throwable.message ?: "Unknown error")
                    )
                }
        }
    }
}
