package com.example.dynamicmaterialthemesync.presentation.theme

import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType

sealed class ThemeStatus {
    object Idle : ThemeStatus()
    object Syncing : ThemeStatus()
    object Synced : ThemeStatus()
    data class Error(val message: String) : ThemeStatus()
}

data class ThemeUiState(
    val loading: Boolean = false,
    val status: ThemeStatus = ThemeStatus.Idle,
    val themeSource: ThemeSource = ThemeSource(ThemeSourceType.SYSTEM_DYNAMIC),
    val supportsDynamicColor: Boolean = false,
    val useDynamicColor: Boolean = false,
    val effectiveThemeModel: ThemeModel? = null,
    val iconStyleConfig: IconStyleConfig = IconStyleConfig(),
    val availableIconStyles: List<IconStyle> = listOf(IconStyle.FILLED, IconStyle.OUTLINED, IconStyle.ROUNDED),
    val lastSyncTime: Long? = null,
    val themeVersion: String? = null,
    val pendingChanges: Boolean = false,
    val availableFonts: List<String> = emptyList(),
    val selectedFontFamily: String? = null
)