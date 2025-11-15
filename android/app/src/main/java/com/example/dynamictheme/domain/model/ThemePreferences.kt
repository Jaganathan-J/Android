package com.example.dynamictheme.domain.model

import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource

data class ThemePreferences(
    val themeSource: ThemeSource = ThemeSource.SystemDynamic,
    val remoteUrl: String? = null,
    val useDynamicColor: Boolean = true,
    val selectedFontFamily: String? = null,
    val selectedIconStyle: IconStyle = IconStyle.Filled,
    val iconWeight: Int? = null,
    val iconGrade: Int? = null,
    val iconOpticalSize: Int? = null,
    val lastThemeVersion: String? = null,
    val lastSyncTimeMillis: Long? = null,
    val applyOnLaunch: Boolean = true
)