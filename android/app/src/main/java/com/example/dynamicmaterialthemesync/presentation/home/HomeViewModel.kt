package com.example.dynamicmaterialthemesync.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicmaterialthemesync.domain.model.ThemeMode
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemePreferencesUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.SyncThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateThemePreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data object Idle : HomeUiState
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Success : HomeUiState
}

data class HomeViewModelState(
    val uiState: HomeUiState = HomeUiState.Idle,
    val preferences: ThemePreferences = ThemePreferences()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val syncThemeUseCase: SyncThemeUseCase,
    private val observeThemePreferences: ObserveThemePreferencesUseCase,
    private val updateThemePreferences: UpdateThemePreferencesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewModelState())
    val state: StateFlow<HomeViewModelState> = _state.asStateFlow()

    init {
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            observeThemePreferences().collect { prefs ->
                _state.update { it.copy(preferences = prefs) }
            }
        }
    }

    fun onSyncThemeClicked() {
        viewModelScope.launch {
            _state.update { it.copy(uiState = HomeUiState.Loading) }
            val result = syncThemeUseCase(forceRefresh = true)
            _state.update {
                result.fold(
                    onSuccess = { HomeViewModelState(uiState = HomeUiState.Success, preferences = it.preferences) },
                    onFailure = { HomeViewModelState(uiState = HomeUiState.Error(it.message ?: "Unknown error"), preferences = it.preferences) }
                )
            }
        }
    }

    fun onThemeModeChanged(mode: ThemeMode) {
        val current = _state.value.preferences
        updatePreferences(current.copy(themeMode = mode))
    }

    fun onUseDynamicColorChanged(enabled: Boolean) {
        val current = _state.value.preferences
        updatePreferences(current.copy(useDynamicColor = enabled))
    }

    fun onUseRemoteThemeChanged(enabled: Boolean) {
        val current = _state.value.preferences
        updatePreferences(current.copy(useRemoteTheme = enabled))
    }

    private fun updatePreferences(preferences: ThemePreferences) {
        viewModelScope.launch {
            updateThemePreferences(preferences)
        }
    }
}