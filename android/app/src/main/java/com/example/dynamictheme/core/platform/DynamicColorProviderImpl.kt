package com.example.dynamictheme.core.platform

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import com.example.dynamictheme.domain.repository.DynamicColorProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicColorProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DynamicColorProvider {

    override fun isSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    override fun getLightScheme(): ColorScheme {
        return if (isSupported()) dynamicLightColorScheme(context) else androidx.compose.material3.lightColorScheme()
    }

    override fun getDarkScheme(): ColorScheme {
        return if (isSupported()) dynamicDarkColorScheme(context) else androidx.compose.material3.darkColorScheme()
    }
}