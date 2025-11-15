package com.example.dynamicmaterialthemesync.domain.repository

import androidx.compose.material3.ColorScheme

interface DynamicColorProvider {
    fun isSupported(): Boolean
    fun getLightScheme(): ColorScheme
    fun getDarkScheme(): ColorScheme
}