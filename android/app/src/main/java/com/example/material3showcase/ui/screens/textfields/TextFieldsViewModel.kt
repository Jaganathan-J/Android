package com.example.material3showcase.ui.screens.textfields

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.usecase.GetTextFieldsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TextFieldsViewModel @Inject constructor(
    private val getTextFieldsConfig: GetTextFieldsConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TextFieldsUiState(isLoading = true))
    val uiState: StateFlow<TextFieldsUiState> = _uiState.asStateFlow()

    init {
        loadFields()
    }

    fun onEvent(event: TextFieldsEvent) {
        when (event) {
            is TextFieldsEvent.OnValueChange -> {
                _uiState.update { state ->
                    val newValues = state.values.toMutableMap()
                    newValues[event.id] = event.value
                    val newErrors = state.errorForField.toMutableMap()
                    newErrors[event.id] = validateField(event.value)
                    state.copy(values = newValues, errorForField = newErrors)
                }
            }
            TextFieldsEvent.Retry -> loadFields()
        }
    }

    private fun loadFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, globalError = null) }
            getTextFieldsConfig().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                fields = list,
                                values = list.associate { it.id to "" },
                                errorForField = list.associate { it.id to null }
                            )
                        }
                    },
                    onFailure = { throwable ->
                        _uiState.update { it.copy(isLoading = false, globalError = throwable.message) }
                    }
                )
            }
        }
    }

    private fun validateField(value: String): String? {
        return if (value.length < 3) "Minimum 3 characters required" else null
    }
}
