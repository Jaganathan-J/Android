package com.example.dynamictheme.presentation.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamictheme.domain.model.ThemePreferences
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource
import com.example.dynamictheme.domain.repository.DynamicColorProvider
import com.example.dynamictheme.domain.repository.PreferencesRepository
import com.example.dynamictheme.domain.usecase.ApplyThemeUseCase
import com.example.dynamictheme.domain.usecase.ObserveThemeUseCase
import com.example.dynamictheme.domain.usecase.ResetThemeUseCase
import com.example.dynamictheme.domain.usecase.SyncThemeUseCase
import com.example.dynamictheme.domain.usecase.ToggleDynamicColorUseCase
import com.example.dynamictheme.domain.usecase.UpdateIconStyleUseCase
import com.example.dynamictheme.domain.usecase.UpdateTypographyUseCase
import com.example.dynamictheme.presentation.theme.state.ThemeStatus
import com.example.dynamictheme.presentation.theme.state.ThemeUiEvent
import com.example.dynamictheme.presentation.theme.state.ThemeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val syncThemeUseCase: SyncThemeUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val toggleDynamicColorUseCase: ToggleDynamicColorUseCase,
    private val updateTypographyUseCase: UpdateTypographyUseCase,
    private val updateIconStyleUseCase: UpdateIconStyleUseCase,
    private val resetThemeUseCase: ResetThemeUseCase,
    private val preferencesRepository: PreferencesRepository,
    private val dynamicColorProvider: DynamicColorProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState: StateFlow<ThemeUiState> = _uiState.asStateFlow()

    private val _themeModel = MutableStateFlow<com.example.dynamictheme.domain.model.ThemeModel?>(null)
    val themeModel: StateFlow<com.example.dynamictheme.domain.model.ThemeModel?> = _themeModel.asStateFlow()

    private val _uiEvents = Channel<ThemeUiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        _uiState.update { it.copy(supportsDynamicColor = dynamicColorProvider.isSupported()) }
        observeTheme()
        viewModelScope.launch {
            preferencesRepository.observeThemePreferences().collect { prefs ->
                _uiState.update { state ->
                    state.copy(
                        themeSource = prefs.themeSource,
                        useDynamicColor = prefs.useDynamicColor,
                        remoteUrl = prefs.remoteUrl,
                        applyOnLaunch = prefs.applyOnLaunch,
                        lastSyncTime = prefs.lastSyncTimeMillis,
                        themeVersion = prefs.lastThemeVersion
                    )
                }
            }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeThemeUseCase().collect { model ->
                _themeModel.value = model
                _uiState.update { state ->
                    state.copy(
                        themeModel = model,
                        iconStyle = model.iconStyle,
                        iconWeight = model.iconWeight,
                        iconGrade = model.iconGrade,
                        iconOpticalSize = model.iconOpticalSize,
                        themeVersion = model.themeVersion,
                        lastSyncTime = model.lastSyncTimeMillis
                    )
                }
            }
        }
    }

    fun onSelectThemeSource(source: ThemeSource) {
        _uiState.update { it.copy(themeSource = source, pendingChanges = true) }
    }

    fun onToggleDynamicColor(enabled: Boolean) {
        _uiState.update { it.copy(useDynamicColor = enabled, pendingChanges = true) }
        viewModelScope.launch {
            toggleDynamicColorUseCase(enabled)
        }
    }

    fun onRequestSync() {
        val source = _uiState.value.themeSource
        val remoteUrl = _uiState.value.remoteUrl
        viewModelScope.launch {
            _uiState.update { it.copy(status = ThemeStatus.Syncing, loading = true) }
            val result = syncThemeUseCase(source, remoteUrl)
            result.onSuccess {
                _uiEvents.send(ThemeUiEvent.ShowMessage("Theme synced"))
                _uiState.update { state -> state.copy(status = ThemeStatus.Synced, loading = false, pendingChanges = false) }
            }.onFailure { throwable ->
                _uiEvents.send(ThemeUiEvent.ShowMessage("Failed to sync theme"))
                _uiState.update { state -> state.copy(status = ThemeStatus.Error(throwable.message ?: "Error"), loading = false) }
            }
        }
    }

    fun onSelectTypography(fontFamily: String) {
        _uiState.update { it.copy(pendingChanges = true) }
        viewModelScope.launch {
            updateTypographyUseCase(fontFamily)
        }
    }

    fun onSelectIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        _uiState.update {
            it.copy(
                iconStyle = style,
                iconWeight = weight,
                iconGrade = grade,
                iconOpticalSize = opticalSize,
                pendingChanges = true
            )
        }
        viewModelScope.launch {
            updateIconStyleUseCase(style, weight, grade, opticalSize)
        }
    }

    fun onApplyChanges() {
        viewModelScope.launch {
            val current = _uiState.value
            val prefs = ThemePreferences(
                themeSource = current.themeSource,
                remoteUrl = current.remoteUrl,
                useDynamicColor = current.useDynamicColor,
                selectedFontFamily = current.themeModel?.typography?.bodyMedium?.fontFamily?.toString(),
                selectedIconStyle = current.iconStyle,
                iconWeight = current.iconWeight,
                iconGrade = current.iconGrade,
                iconOpticalSize = current.iconOpticalSize,
                lastThemeVersion = current.themeVersion,
                lastSyncTimeMillis = current.lastSyncTime,
                applyOnLaunch = current.applyOnLaunch
            )
            applyThemeUseCase(prefs)
            _uiState.update { it.copy(pendingChanges = false) }
        }
    }

    fun onResetDefaults() {
        viewModelScope.launch {
            resetThemeUseCase()
            _uiState.update { ThemeUiState(supportsDynamicColor = dynamicColorProvider.isSupported()) }
        }
    }

    fun onUpdateRemoteUrl(url: String) {
        _uiState.update { it.copy(remoteUrl = url, pendingChanges = true) }
    }

    fun onToggleApplyOnLaunch(apply: Boolean) {
        _uiState.update { it.copy(applyOnLaunch = apply, pendingChanges = true) }
    }
}