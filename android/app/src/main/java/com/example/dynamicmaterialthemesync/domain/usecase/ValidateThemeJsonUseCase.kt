package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens

class ValidateThemeJsonUseCase {
    operator fun invoke(tokens: ThemeTokens) {
        if (!tokens.schemaVersion.startsWith("1.")) {
            throw IllegalArgumentException("Unsupported schema version: ${tokens.schemaVersion}")
        }
        requireNotNull(tokens.colors.light) { "Light color tokens required" }
        requireNotNull(tokens.colors.dark) { "Dark color tokens required" }
    }
}