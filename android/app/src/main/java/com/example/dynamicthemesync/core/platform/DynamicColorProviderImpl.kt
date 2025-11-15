package com.example.dynamicthemesync.core.platform

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicColorProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : com.example.dynamicthemesync.domain.repository.DynamicColorProvider {

    override fun isSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    override fun getLightScheme(): ColorScheme = dynamicLightColorScheme(context)

    override fun getDarkScheme(): ColorScheme = dynamicDarkColorScheme(context)
}