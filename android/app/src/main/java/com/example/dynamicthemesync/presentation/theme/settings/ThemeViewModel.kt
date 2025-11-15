package com.example.dynamicthemesync.presentation.theme.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeSourceType
import com.example.dynamicthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicthemesync.domain.repository.IconRepository
import com.example.dynamicthemesync.domain.repository.PreferencesRepository
import com.example.dynamicthemesync.domain.repository.ThemeRepository
import com.example.dynamicthemesync.domain.usecase.ApplyThemeUseCase
import com.example.dynamicthemesync.domain.usecase.ResetThemeUseCase
import com.example.dynamicthemesync.domain.usecase.SyncThemeUseCase
import com.example.dynamicthemesync.domain.usecase.ToggleDynamicColorUseCase
import com.example.dynamicthemesync.domain.usecase.UpdateIconStyleUseCase
import com.example.dynamicthemesync.domain.usecase.UpdateTypographyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ThemeStatus {
    data object Idle : ThemeStatus
    data object Syncing : ThemeStatus
    data class Error(val message: String) : ThemeStatus
    data object Synced : ThemeStatus
}

data class ThemeUiState(
    val loading: Boolean = false,
    val status: ThemeStatus = ThemeStatus.Idle,
    val themeSource: ThemeSourceType = ThemeSourceType.SYSTEM_DYNAMIC,
    val supportsDynamicColor: Boolean = false,
    val useDynamicColor: Boolean = true,
    val iconStyle: IconStyle = IconStyle.OUTLINED,
    val iconWeight: Int? = null,
    val iconGrade: Int? = null,
    val iconOpticalSize: Int? = null,
    val availableIconStyles: List<IconStyle> = listOf(IconStyle.FILLED, IconStyle.OUTLINED, IconStyle.ROUNDED),
    val lastSyncTime: Long? = null,
    val themeVersion: String? = null,
    val pendingChanges: Boolean = false,
    val selectedFontFamily: String? = null
)

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val themeRepository: ThemeRepository,
    private val iconRepository: IconRepository,
    private val dynamicColorProvider: DynamicColorProvider,
    private val syncThemeUseCase: SyncThemeUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val toggleDynamicColorUseCase: ToggleDynamicColorUseCase,
    private val updateTypographyUseCase: UpdateTypographyUseCase,
    private val updateIconStyleUseCase: UpdateIconStyleUseCase,
    private val resetThemeUseCase: ResetThemeUseCase
) : ViewModel() {

    private val internalStatus = MutableStateFlow<ThemeStatus>(ThemeStatus.Idle)
    private val pendingChanges = MutableStateFlow(false)

    val uiState: StateFlow<ThemeUiState> = combine(
        preferencesRepository.observeThemePreferences(),
        iconRepository.getIconStyle(),
        themeRepository.getLastSyncInfoFlow(),
        internalStatus,
        pendingChanges
    ) { prefs, iconConfig, syncInfo, status, pending ->
        ThemeUiState(
            loading = status is ThemeStatus.Syncing,
            status = status,
            themeSource = prefs.themeSource,
            supportsDynamicColor = dynamicColorProvider.isSupported(),
            useDynamicColor = prefs.useDynamicColor,
            iconStyle = iconConfig.style,
            iconWeight = iconConfig.weight,
            iconGrade = iconConfig.grade,
            iconOpticalSize = iconConfig.opticalSize,
            availableIconStyles = iconRepository.supportedStyles(),
            lastSyncTime = syncInfo.lastSyncTimeMillis,
            themeVersion = syncInfo.themeVersion,
            pendingChanges = pending,
            selectedFontFamily = prefs.selectedFontFamily
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, ThemeUiState())

    init {
        viewModelScope.launch {
            if (dynamicColorProvider.isSupported()) {
                val prefs = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, ThemePreferences(
                    themeSource = ThemeSourceType.SYSTEM_DYNAMIC,
                    remoteUrl = null,
                    useDynamicColor = true,
                    selectedFontFamily = null,
                    selectedIconStyle = IconStyle.OUTLINED,
                    iconWeight = null,
                    iconGrade = null,
                    iconOpticalSize = null,
                    lastThemeVersion = null,
                    lastSyncTimeMillis = null
                )).value
                if (prefs.lastSyncTimeMillis == null) {
                    onRequestSync()
                }
            }
        }
    }

    fun onSelectThemeSource(source: ThemeSourceType) {
        viewModelScope.launch {
            val current = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            preferencesRepository.updateThemePreferences(current.copy(themeSource = source))
            pendingChanges.value = true
        }
    }

    fun onToggleDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            val current = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            toggleDynamicColorUseCase(current, enabled)
            pendingChanges.value = true
        }
    }

    fun onRequestSync() {
        viewModelScope.launch {
            internalStatus.value = ThemeStatus.Syncing
            val prefs = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            val result = syncThemeUseCase(prefs.themeSource, prefs.remoteUrl)
            internalStatus.value = result.fold(
                onSuccess = { ThemeStatus.Synced },
                onFailure = { ThemeStatus.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun onSelectTypography(fontFamily: String?) {
        viewModelScope.launch {
            val current = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            updateTypographyUseCase(current, fontFamily)
            pendingChanges.value = true
        }
    }

    fun onSelectIconStyle(style: IconStyle) {
        val state = uiState.value
        onUpdateIconAxes(style = style, weight = state.iconWeight, grade = state.iconGrade, opsz = state.iconOpticalSize)
    }

    fun onUpdateIconAxes(style: IconStyle? = null, weight: Int?, grade: Int?, opsz: Int?) {
        viewModelScope.launch {
            val currentPrefs = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            val targetStyle = style ?: currentPrefs.selectedIconStyle
            updateIconStyleUseCase(currentPrefs, targetStyle, weight, grade, opsz)
            pendingChanges.value = true
        }
    }

    fun onApplyChanges() {
        viewModelScope.launch {
            val prefs = preferencesRepository.observeThemePreferences().stateIn(viewModelScope, SharingStarted.Eagerly, uiState.value.toPreferences()).value
            applyThemeUseCase(prefs)
            pendingChanges.value = false
        }
    }

    fun onResetDefaults() {
        viewModelScope.launch {
            resetThemeUseCase()
            pendingChanges.value = false
            internalStatus.value = ThemeStatus.Idle
        }
    }

    private fun ThemeUiState.toPreferences(): ThemePreferences = ThemePreferences(
        themeSource = themeSource,
        remoteUrl = null,
        useDynamicColor = useDynamicColor,
        selectedFontFamily = selectedFontFamily,
        selectedIconStyle = iconStyle,
        iconWeight = iconWeight,
        iconGrade = iconGrade,
        iconOpticalSize = iconOpticalSize,
        lastThemeVersion = themeVersion,
        lastSyncTimeMillis = lastSyncTime
    )
}

private fun ThemeRepository.getLastSyncInfoFlow() = kotlinx.coroutines.flow.flow {
    emit(getLastSyncInfo())
}