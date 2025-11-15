package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.usecase.ApplyThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ResetThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.SyncThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ToggleDynamicColorUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateIconStyleUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateTypographyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ThemeStatus {
    object Idle : ThemeStatus()
    object Syncing : ThemeStatus()
    object Synced : ThemeStatus()
    data class Error(val message: String) : ThemeStatus()
}

data class ThemeUiState(
    val loading: Boolean = true,
    val status: ThemeStatus = ThemeStatus.Idle,
    val supportsDynamicColor: Boolean = false,
    val themeModel: ThemeModel,
    val useDynamicColor: Boolean,
    val themeSource: ThemeSource,
    val iconStyle: IconStyleConfig,
    val pendingChanges: Boolean = false
)

sealed class ThemeIntent {
    data class SelectThemeSource(val source: ThemeSource) : ThemeIntent()
    data class ToggleDynamicColor(val enabled: Boolean) : ThemeIntent()
    object RequestSync : ThemeIntent()
    data class SelectTypography(val fontFamily: String) : ThemeIntent()
    data class SelectIconStyle(val style: IconStyle) : ThemeIntent()
    data class UpdateIconAxes(val weight: Int?, val grade: Int?, val opticalSize: Int?) : ThemeIntent()
    object ApplyChanges : ThemeIntent()
    object ResetDefaults : ThemeIntent()
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val preferencesRepository: PreferencesRepository,
    private val syncThemeUseCase: SyncThemeUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val toggleDynamicColorUseCase: ToggleDynamicColorUseCase,
    private val updateTypographyUseCase: UpdateTypographyUseCase,
    private val updateIconStyleUseCase: UpdateIconStyleUseCase,
    private val resetThemeUseCase: ResetThemeUseCase,
    private val dynamicColorProvider: com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemeUiState>

    val uiState: StateFlow<ThemeUiState>

    init {
        val defaultSource = ThemeSource(ThemeSourceType.SYSTEM_DYNAMIC)
        val defaultIconStyle = IconStyleConfig(IconStyle.OUTLINED, 400, 0, 24)
        val defaultModel = com.example.dynamicmaterialthemesync.domain.usecase.BuildMaterialThemeUseCase()(
            tokens = null,
            preferences = ThemePreferences(
                themeSource = defaultSource,
                useDynamicColor = true,
                selectedFontFamily = null,
                iconStyle = IconStyle.OUTLINED,
                iconWeight = 400,
                iconGrade = 0,
                iconOpticalSize = 24,
                applyOnLaunch = true,
                lastThemeVersion = null,
                lastSyncTimeMillis = null
            ),
            iconStyleConfig = defaultIconStyle,
            dynamicColorProvider = dynamicColorProvider
        )
        _uiState = MutableStateFlow(
            ThemeUiState(
                loading = true,
                status = ThemeStatus.Idle,
                supportsDynamicColor = dynamicColorProvider.isSupported(),
                themeModel = defaultModel,
                useDynamicColor = true,
                themeSource = defaultSource,
                iconStyle = defaultIconStyle
            )
        )
        uiState = _uiState.asStateFlow()

        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            observeThemeUseCase().collectLatest { model ->
                val prefs = preferencesRepository.observeThemePreferences().collectAsStateOnce()
                _uiState.update {
                    it.copy(
                        loading = false,
                        themeModel = model,
                        useDynamicColor = prefs.useDynamicColor,
                        themeSource = prefs.themeSource,
                        iconStyle = model.iconStyleConfig,
                        status = ThemeStatus.Idle
                    )
                }
            }
        }
    }

    fun onIntent(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SelectThemeSource -> selectThemeSource(intent.source)
            is ThemeIntent.ToggleDynamicColor -> toggleDynamicColor(intent.enabled)
            ThemeIntent.RequestSync -> requestSync()
            is ThemeIntent.SelectTypography -> selectTypography(intent.fontFamily)
            is ThemeIntent.SelectIconStyle -> selectIconStyle(intent.style)
            is ThemeIntent.UpdateIconAxes -> updateIconAxes(intent.weight, intent.grade, intent.opticalSize)
            ThemeIntent.ApplyChanges -> applyChanges()
            ThemeIntent.ResetDefaults -> resetDefaults()
        }
    }

    private fun selectThemeSource(source: ThemeSource) {
        viewModelScope.launch {
            val prefs = preferencesRepository.observeThemePreferences().collectAsStateOnce()
            val updated = prefs.copy(themeSource = source)
            preferencesRepository.updateThemePreferences(updated)
            _uiState.update { it.copy(themeSource = source, pendingChanges = true) }
        }
    }

    private fun toggleDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            toggleDynamicColorUseCase(enabled)
            _uiState.update { it.copy(useDynamicColor = enabled, pendingChanges = true) }
        }
    }

    private fun requestSync() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = ThemeStatus.Syncing) }
            val source = _uiState.value.themeSource
            val result = syncThemeUseCase(source)
            _uiState.update {
                if (result.isSuccess) it.copy(status = ThemeStatus.Synced) else it.copy(
                    status = ThemeStatus.Error(result.exceptionOrNull()?.message ?: "Sync failed")
                )
            }
        }
    }

    private fun selectTypography(fontFamily: String) {
        viewModelScope.launch {
            updateTypographyUseCase(fontFamily)
            _uiState.update { it.copy(pendingChanges = true) }
        }
    }

    private fun selectIconStyle(style: IconStyle) {
        viewModelScope.launch {
            val current = _uiState.value.iconStyle
            val updated = current.copy(style = style)
            updateIconStyleUseCase(updated)
            _uiState.update { it.copy(iconStyle = updated, pendingChanges = true) }
        }
    }

    private fun updateIconAxes(weight: Int?, grade: Int?, opticalSize: Int?) {
        viewModelScope.launch {
            val updated = _uiState.value.iconStyle.copy(
                weight = weight ?: _uiState.value.iconStyle.weight,
                grade = grade ?: _uiState.value.iconStyle.grade,
                opticalSize = opticalSize ?: _uiState.value.iconStyle.opticalSize
            )
            updateIconStyleUseCase(updated)
            _uiState.update { it.copy(iconStyle = updated, pendingChanges = true) }
        }
    }

    private fun applyChanges() {
        viewModelScope.launch {
            val prefs = preferencesRepository.observeThemePreferences().collectAsStateOnce()
            applyThemeUseCase(prefs)
            _uiState.update { it.copy(pendingChanges = false) }
        }
    }

    private fun resetDefaults() {
        viewModelScope.launch {
            resetThemeUseCase()
            _uiState.update { it.copy(pendingChanges = false) }
        }
    }
}

private suspend fun <T> kotlinx.coroutines.flow.Flow<T>.collectAsStateOnce(): T {
    var value: T? = null
    this.collect { emitted ->
        value = emitted
        return@collect
    }
    return requireNotNull(value)
}