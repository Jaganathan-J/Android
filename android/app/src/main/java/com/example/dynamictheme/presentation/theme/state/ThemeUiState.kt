package com.example.dynamictheme.presentation.theme.state

import com.example.dynamictheme.domain.model.ThemeModel
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource

sealed class ThemeStatus {
    object Idle : ThemeStatus()
    object Syncing : ThemeStatus()
    object Synced : ThemeStatus()
    data class Error(val message: String) : ThemeStatus()
}

data class ThemeUiState(
    val loading: Boolean = false,
    val status: ThemeStatus = ThemeStatus.Idle,
    val themeSource: ThemeSource = ThemeSource.SystemDynamic,
    val supportsDynamicColor: Boolean = false,
    val useDynamicColor: Boolean = true,
    val themeModel: ThemeModel? = null,
    val iconStyle: IconStyle = IconStyle.Filled,
    val iconWeight: Int? = null,
    val iconGrade: Int? = null,
    val iconOpticalSize: Int? = null,
    val lastSyncTime: Long? = null,
    val themeVersion: String? = null,
    val pendingChanges: Boolean = false,
    val remoteUrl: String? = null,
    val applyOnLaunch: Boolean = true
)

sealed class ThemeUiEvent {
    data class ShowMessage(val message: String) : ThemeUiEvent()
}