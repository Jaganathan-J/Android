package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import javax.inject.Inject

class ValidateThemeJsonUseCase @Inject constructor() {
    operator fun invoke(tokens: ThemeTokens): Boolean {
        // Basic schema validation based on TDD
        if (!tokens.schemaVersion.startsWith("1.")) return false
        val light = tokens.colors.light
        val dark = tokens.colors.dark
        fun hasMinimum(t: com.example.dynamicmaterialthemesync.domain.model.ColorSchemeTokens): Boolean {
            return t.primary != null &&
                t.onPrimary != null &&
                t.background != null &&
                t.surface != null &&
                t.onSurface != null
        }
        if (!hasMinimum(light)) return false
        if (!hasMinimum(dark)) return false
        return true
    }
}