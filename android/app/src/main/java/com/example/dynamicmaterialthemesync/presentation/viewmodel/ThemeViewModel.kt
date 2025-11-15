package com.example.dynamicmaterialthemesync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import com.example.dynamicmaterialthemesync.core.util.Result
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemeModeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.RefreshThemeTokensUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.SetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    observeThemeUseCase: ObserveThemeUseCase,
    observeThemeModeUseCase: ObserveThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
    private val refreshThemeTokensUseCase: RefreshThemeTokensUseCase
) : ViewModel() {

    private val _themeState = MutableStateFlow(ThemeUiState())
    val themeState: StateFlow<ThemeUiState> = _themeState.asStateFlow()

    init {
        observeThemeUseCase()
            .onEach { tokens ->
                _themeState.value = _themeState.value.copy(tokensVersion = tokens.version)
            }
            .launchIn(viewModelScope)

        observeThemeModeUseCase()
            .onEach { mode ->
                _themeState.value = _themeState.value.copy(mode = mode)
            }
            .launchIn(viewModelScope)

        // initial refresh
        refreshTokens()
    }

    fun cycleThemeMode() {
        val nextMode = when (_themeState.value.mode) {
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.SYSTEM
        }
        viewModelScope.launch {
            setThemeModeUseCase(nextMode)
        }
    }

    fun refreshTokens() {
        viewModelScope.launch {
            _themeState.value = _themeState.value.copy(isLoading = true)
            when (val result = refreshThemeTokensUseCase()) {
                is Result.Success -> {
                    _themeState.value = _themeState.value.copy(isLoading = false)
                }
                is Result.Error -> {
                    _themeState.value = _themeState.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to fetch theme: ${result.throwable.message ?: "unknown"}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _themeState.value = _themeState.value.copy(errorMessage = null)
    }
}

data class ThemeUiState(
    val mode: ThemeMode = ThemeMode.SYSTEM,
    val tokensVersion: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)