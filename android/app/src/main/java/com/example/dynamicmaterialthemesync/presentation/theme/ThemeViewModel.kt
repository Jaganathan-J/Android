package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import com.example.dynamicmaterialthemesync.domain.usecase.ApplyThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ResetThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.SyncThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ToggleDynamicColorUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateIconStyleUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateTypographyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ThemeIntent {
    data class SelectThemeSource(val source: ThemeSource) : ThemeIntent()
    data class ToggleDynamicColor(val enabled: Boolean) : ThemeIntent()
    object RequestSync : ThemeIntent()
    data class SelectTypography(val fontFamily: String) : ThemeIntent()
    data class SelectIconStyle(val style: IconStyle) : ThemeIntent()
    data class UpdateIconAxes(val axes: IconAxes) : ThemeIntent()
    object ApplyChanges : ThemeIntent()
    object ResetDefaults : ThemeIntent()
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val syncThemeUseCase: SyncThemeUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val toggleDynamicColorUseCase: ToggleDynamicColorUseCase,
    private val updateTypographyUseCase: UpdateTypographyUseCase,
    private val updateIconStyleUseCase: UpdateIconStyleUseCase,
    private val resetThemeUseCase: ResetThemeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState: StateFlow<ThemeUiState> = _uiState.asStateFlow()

    init {
        observeThemeUseCase()
            .onEach { themeModel ->
                _uiState.value = _uiState.value.copy(
                    effectiveThemeModel = themeModel,
                    supportsDynamicColor = themeModel.supportsDynamicColor,
                    useDynamicColor = themeModel.useDynamicColor,
                    themeSource = themeModel.themeSource,
                    iconStyleConfig = themeModel.iconStyleConfig,
                    lastSyncTime = themeModel.lastSyncTimeMillis,
                    themeVersion = themeModel.themeVersion,
                    loading = false,
                    status = ThemeStatus.Idle
                )
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SelectThemeSource -> onSelectThemeSource(intent.source)
            is ThemeIntent.ToggleDynamicColor -> onToggleDynamicColor(intent.enabled)
            ThemeIntent.RequestSync -> onRequestSync()
            is ThemeIntent.SelectTypography -> onSelectTypography(intent.fontFamily)
            is ThemeIntent.SelectIconStyle -> onSelectIconStyle(intent.style)
            is ThemeIntent.UpdateIconAxes -> onUpdateIconAxes(intent.axes)
            ThemeIntent.ApplyChanges -> onApplyChanges()
            ThemeIntent.ResetDefaults -> onResetDefaults()
        }
    }

    private fun onSelectThemeSource(source: ThemeSource) {
        _uiState.value = _uiState.value.copy(themeSource = source, pendingChanges = true)
    }

    private fun onToggleDynamicColor(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(useDynamicColor = enabled, pendingChanges = true)
        viewModelScope.launch {
            toggleDynamicColorUseCase(enabled)
        }
    }

    private fun onRequestSync() {
        val source = _uiState.value.themeSource
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = ThemeStatus.Syncing, loading = true)
            val result = syncThemeUseCase(source)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(status = ThemeStatus.Synced, loading = false)
            } else {
                _uiState.value.copy(status = ThemeStatus.Error(result.exceptionOrNull()?.message ?: "Unknown error"), loading = false)
            }
        }
    }

    fun onSelectTypography(fontFamily: String) {
        _uiState.value = _uiState.value.copy(selectedFontFamily = fontFamily, pendingChanges = true)
        viewModelScope.launch {
            updateTypographyUseCase(fontFamily)
        }
    }

    fun onSelectIconStyle(style: IconStyle) {
        val currentAxes = _uiState.value.iconStyleConfig.axes
        _uiState.value = _uiState.value.copy(iconStyleConfig = _uiState.value.iconStyleConfig.copy(style = style), pendingChanges = true)
        viewModelScope.launch {
            updateIconStyleUseCase(style, currentAxes)
        }
    }

    private fun onUpdateIconAxes(axes: IconAxes) {
        val currentStyle = _uiState.value.iconStyleConfig.style
        _uiState.value = _uiState.value.copy(iconStyleConfig = _uiState.value.iconStyleConfig.copy(axes = axes), pendingChanges = true)
        viewModelScope.launch {
            updateIconStyleUseCase(currentStyle, axes)
        }
    }

    fun onApplyChanges() {
        val state = _uiState.value
        val themeModel = state.effectiveThemeModel ?: return
        viewModelScope.launch {
            val prefs = ThemePreferences(
                themeSource = state.themeSource,
                useDynamicColor = state.useDynamicColor,
                selectedFontFamily = state.selectedFontFamily,
                selectedIconStyle = state.iconStyleConfig.style,
                iconAxes = state.iconStyleConfig.axes,
                applyOnLaunch = true,
                lastThemeVersion = themeModel.themeVersion,
                lastSyncTimeMillis = themeModel.lastSyncTimeMillis
            )
            applyThemeUseCase(prefs)
            _uiState.value = _uiState.value.copy(pendingChanges = false)
        }
    }

    private fun onResetDefaults() {
        viewModelScope.launch {
            resetThemeUseCase()
            _uiState.value = ThemeUiState()
        }
    }
}