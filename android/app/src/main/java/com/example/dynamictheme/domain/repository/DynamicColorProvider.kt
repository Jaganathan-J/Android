package com.example.dynamictheme.domain.repository

import androidx.compose.material3.ColorScheme

interface DynamicColorProvider {
    fun isSupported(): Boolean
    fun getLightScheme(): ColorScheme
    fun getDarkScheme(): ColorScheme
}