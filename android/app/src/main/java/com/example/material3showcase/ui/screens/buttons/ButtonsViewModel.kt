package com.example.material3showcase.ui.screens.buttons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.usecase.GetButtonsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    private val getButtonsConfig: GetButtonsConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ButtonsUiState(isLoading = true))
    val uiState: StateFlow<ButtonsUiState> = _uiState.asStateFlow()

    init {
        loadButtons()
    }

    fun onEvent(event: ButtonsEvent) {
        when (event) {
            is ButtonsEvent.OnButtonClick -> {
                _uiState.update { it.copy(lastClickedLabel = event.model.label) }
            }
            ButtonsEvent.Retry -> loadButtons()
        }
    }

    private fun loadButtons() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getButtonsConfig().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.update { it.copy(isLoading = false, buttons = list, errorMessage = null) }
                    },
                    onFailure = { throwable ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = throwable.message ?: "Failed to load buttons"
                            )
                        }
                    }
                )
            }
        }
    }
}
