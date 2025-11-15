package com.example.dynamicmaterialthemesync.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ThemeStatus {
    object Idle : ThemeStatus()
    object Syncing : ThemeStatus()
    object Synced : ThemeStatus()
    data class Error(val message: String) : ThemeStatus()
}

data class ThemeUiState(
    val loading: Boolean = true,
    val status: ThemeStatus = ThemeStatus.Idle,
    val themeModel: ThemeModel,
    val themeSource: ThemeSource,
    val supportsDynamicColor: Boolean,
    val useDynamicColor: Boolean,
    val iconStyle: IconStyle,
    val lastSyncTime: Long?,
    val themeVersion: String?,
    val pendingChanges: Boolean = false,
    val snackbarMessage: String? = null
)

sealed class ThemeIntent {
    data class SelectThemeSource(val source: ThemeSource) : ThemeIntent()
    data class ToggleDynamicColor(val enabled: Boolean) : ThemeIntent()
    object RequestSync : ThemeIntent()
    data class SelectTypography(val fontFamily: String) : ThemeIntent()
    data class SelectIconStyle(val style: IconStyle, val weight: Int?, val grade: Int?, val opticalSize: Int?) : ThemeIntent()
    object ApplyChanges : ThemeIntent()
    object ResetDefaults : ThemeIntent()
    object SnackbarShown : ThemeIntent()
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    observeThemeUseCase: ObserveThemeUseCase,
    private val syncThemeUseCase: SyncThemeUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val toggleDynamicColorUseCase: ToggleDynamicColorUseCase,
    private val updateTypographyUseCase: UpdateTypographyUseCase,
    private val updateIconStyleUseCase: UpdateIconStyleUseCase,
    private val resetThemeUseCase: ResetThemeUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemeUiState>

    val uiState: StateFlow<ThemeUiState>
        get() = _uiState.asStateFlow()

    init {
        val initialModel = observeThemeUseCase()
        // Need an initial value; will be replaced on first emission.
        val placeholderModel = ThemeModel(
            colorScheme = androidx.compose.material3.lightColorScheme(),
            typography = androidx.compose.material3.Typography(),
            shapes = androidx.compose.material3.Shapes(),
            elevationTokens = null,
            iconStyleConfig = com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig(),
            themeSource = ThemeSource.SYSTEM_DYNAMIC,
            useDynamicColor = true,
            supportsDynamicColor = false,
            themeVersion = null,
            lastSyncTimeMillis = null
        )
        _uiState = MutableStateFlow(
            ThemeUiState(
                loading = true,
                status = ThemeStatus.Idle,
                themeModel = placeholderModel,
                themeSource = placeholderModel.themeSource,
                supportsDynamicColor = placeholderModel.supportsDynamicColor,
                useDynamicColor = placeholderModel.useDynamicColor,
                iconStyle = placeholderModel.iconStyleConfig.style,
                lastSyncTime = placeholderModel.lastSyncTimeMillis,
                themeVersion = placeholderModel.themeVersion
            )
        )

        viewModelScope.launch {
            initialModel.collect { model ->
                _uiState.update { state ->
                    state.copy(
                        loading = false,
                        themeModel = model,
                        themeSource = model.themeSource,
                        supportsDynamicColor = model.supportsDynamicColor,
                        useDynamicColor = model.useDynamicColor,
                        iconStyle = model.iconStyleConfig.style,
                        lastSyncTime = model.lastSyncTimeMillis,
                        themeVersion = model.themeVersion
                    )
                }
            }
        }
    }

    fun onIntent(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SelectThemeSource -> onSelectThemeSource(intent.source)
            is ThemeIntent.ToggleDynamicColor -> onToggleDynamicColor(intent.enabled)
            is ThemeIntent.RequestSync -> onRequestSync()
            is ThemeIntent.SelectTypography -> onSelectTypography(intent.fontFamily)
            is ThemeIntent.SelectIconStyle -> onSelectIconStyle(intent.style, intent.weight, intent.grade, intent.opticalSize)
            ThemeIntent.ApplyChanges -> onApplyChanges()
            ThemeIntent.ResetDefaults -> onResetDefaults()
            ThemeIntent.SnackbarShown -> onSnackbarShown()
        }
    }

    private fun onSelectThemeSource(source: ThemeSource) {
        _uiState.update { it.copy(themeSource = source, pendingChanges = true) }
    }

    private fun onToggleDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            toggleDynamicColorUseCase(enabled)
        }
        _uiState.update { it.copy(useDynamicColor = enabled, pendingChanges = true) }
    }

    private fun onRequestSync() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = ThemeStatus.Syncing) }
            val prefs = ThemePreferences(themeSource = _uiState.value.themeSource)
            val result = syncThemeUseCase(prefs)
            _uiState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(status = ThemeStatus.Synced, snackbarMessage = "Theme synced")
                    },
                    onFailure = {
                        state.copy(status = ThemeStatus.Error(it.message ?: "Sync failed"), snackbarMessage = "Failed to sync theme")
                    }
                )
            }
        }
    }

    private fun onSelectTypography(fontFamily: String) {
        viewModelScope.launch {
            updateTypographyUseCase(fontFamily)
        }
        _uiState.update { it.copy(pendingChanges = true) }
    }

    private fun onSelectIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        viewModelScope.launch {
            updateIconStyleUseCase(style, weight, grade, opticalSize)
        }
        _uiState.update { it.copy(iconStyle = style, pendingChanges = true) }
    }

    private fun onApplyChanges() {
        viewModelScope.launch {
            val state = _uiState.value
            val currentPrefs = ThemePreferences(
                themeSource = state.themeSource,
                useDynamicColor = state.useDynamicColor
            )
            applyThemeUseCase(currentPrefs)
            _uiState.update { it.copy(pendingChanges = false) }
        }
    }

    private fun onResetDefaults() {
        viewModelScope.launch {
            resetThemeUseCase()
        }
    }

    private fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}