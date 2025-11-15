package com.example.dynamicmaterialthemesync.data.mapper

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dynamicmaterialthemesync.data.remote.dto.RemoteThemeColorDto
import com.example.dynamicmaterialthemesync.data.remote.dto.RemoteThemeTokensDto
import com.example.dynamicmaterialthemesync.domain.model.ThemeColorToken
import com.example.dynamicmaterialthemesync.domain.model.ThemeElevationToken
import com.example.dynamicmaterialthemesync.domain.model.ThemeShapeToken
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeTypographyToken

/**
 * Mapping utilities from remote DTOs to domain model.
 */

fun RemoteThemeTokensDto.toDomain(): ThemeTokens {
    val colorTokens = colors.orEmpty().map(RemoteThemeColorDto::toDomain)
    val typographyTokens = typography.orEmpty().map { dto ->
        ThemeTypographyToken(
            name = dto.name,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = dto.fontWeight?.let { FontWeight(it) } ?: FontWeight.Normal,
                fontSize = (dto.fontSizeSp ?: 14f).sp
            )
        )
    }
    val shapeTokens = shapes.orEmpty().map { dto ->
        ThemeShapeToken(
            name = dto.name,
            shape = RoundedCornerShape((dto.cornerRadius ?: 4f).dp)
        )
    }
    val elevationTokens = elevation.orEmpty().map { dto ->
        ThemeElevationToken(
            level = dto.level,
            value = dto.dp
        )
    }
    return ThemeTokens(
        colorTokens = colorTokens,
        typographyTokens = typographyTokens,
        shapeTokens = shapeTokens,
        elevationTokens = elevationTokens,
        version = version
    )
}

private fun RemoteThemeColorDto.toDomain(): ThemeColorToken = ThemeColorToken(
    name = name,
    light = parseColor(light),
    dark = parseColor(dark)
)

private fun parseColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val parsed = clean.toLongOrNull(16) ?: 0xFF000000
    return if (clean.length <= 6) {
        // RGB
        Color(parsed or 0xFF000000)
    } else {
        // ARGB
        Color(parsed)
    }
}