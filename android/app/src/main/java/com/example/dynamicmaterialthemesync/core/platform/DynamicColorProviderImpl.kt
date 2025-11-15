package com.example.dynamicmaterialthemesync.core.platform

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider

class DynamicColorProviderImpl(
    private val appContext: Context
) : DynamicColorProvider {

    override fun isSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    override fun getLightScheme(): ColorScheme {
        return if (isSupported()) dynamicLightColorScheme(appContext) else androidx.compose.material3.lightColorScheme()
    }

    override fun getDarkScheme(): ColorScheme {
        return if (isSupported()) dynamicDarkColorScheme(appContext) else androidx.compose.material3.darkColorScheme()
    }
}