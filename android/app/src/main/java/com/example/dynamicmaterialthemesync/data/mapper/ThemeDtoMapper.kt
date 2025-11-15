package com.example.dynamicmaterialthemesync.data.mapper

import androidx.compose.ui.graphics.Color
import com.example.dynamicmaterialthemesync.data.source.remote.ColorRolesDto
import com.example.dynamicmaterialthemesync.data.source.remote.ColorsDto
import com.example.dynamicmaterialthemesync.data.source.remote.ElevationDto
import com.example.dynamicmaterialthemesync.data.source.remote.IconsDto
import com.example.dynamicmaterialthemesync.data.source.remote.MetaDto
import com.example.dynamicmaterialthemesync.data.source.remote.ShapesDto
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeJsonDto
import com.example.dynamicmaterialthemesync.data.source.remote.TypographyDto
import com.example.dynamicmaterialthemesync.domain.model.ColorRoleTokens
import com.example.dynamicmaterialthemesync.domain.model.ElevationTokens
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconTokens
import com.example.dynamicmaterialthemesync.domain.model.ShapeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeColorTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeMeta
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.model.TypographyTokens

object ThemeDtoMapper {
    fun map(dto: ThemeJsonDto): ThemeTokens {
        return ThemeTokens(
            schemaVersion = dto.schemaVersion,
            themeVersion = dto.themeVersion ?: "",
            name = dto.name ?: "",
            colors = mapColors(dto.colors),
            typography = dto.typography?.let { mapTypography(it) },
            shapes = dto.shapes?.let { mapShapes(it) },
            elevation = dto.elevation?.let { mapElevation(it) },
            icons = dto.icons?.let { mapIcons(it) },
            meta = dto.meta?.let { mapMeta(it) }
        )
    }

    private fun mapColors(dto: ColorsDto?): ThemeColorTokens {
        return ThemeColorTokens(
            light = dto?.light?.let { mapColorRoles(it) },
            dark = dto?.dark?.let { mapColorRoles(it) }
        )
    }

    private fun mapColorRoles(dto: ColorRolesDto): ColorRoleTokens {
        return ColorRoleTokens(
            primary = dto.primary?.toColor(),
            onPrimary = dto.onPrimary?.toColor(),
            primaryContainer = dto.primaryContainer?.toColor(),
            onPrimaryContainer = dto.onPrimaryContainer?.toColor(),
            secondary = dto.secondary?.toColor(),
            onSecondary = dto.onSecondary?.toColor(),
            background = dto.background?.toColor(),
            onBackground = dto.onBackground?.toColor(),
            surface = dto.surface?.toColor(),
            onSurface = dto.onSurface?.toColor(),
            surfaceVariant = dto.surfaceVariant?.toColor(),
            onSurfaceVariant = dto.onSurfaceVariant?.toColor(),
            tertiary = dto.tertiary?.toColor(),
            error = dto.error?.toColor(),
            onError = dto.onError?.toColor(),
            outline = dto.outline?.toColor(),
            inverseSurface = dto.inverseSurface?.toColor(),
            inverseOnSurface = dto.inverseOnSurface?.toColor()
        )
    }

    private fun mapTypography(dto: TypographyDto): TypographyTokens {
        return TypographyTokens(
            fontFamily = dto.fontFamily ?: "",
            fallback = dto.fallback ?: emptyList(),
            weightMapping = dto.weightMapping ?: emptyMap(),
            sizeSp = dto.sizeSp ?: emptyMap(),
            lineHeightSp = dto.lineHeightSp ?: emptyMap(),
            letterSpacingEm = dto.letterSpacingEm ?: emptyMap()
        )
    }

    private fun mapShapes(dto: ShapesDto): ShapeTokens {
        return ShapeTokens(
            cornerFamily = dto.cornerFamily ?: "rounded",
            extraSmall = dto.extraSmall,
            small = dto.small,
            medium = dto.medium,
            large = dto.large,
            extraLarge = dto.extraLarge
        )
    }

    private fun mapElevation(dto: ElevationDto): ElevationTokens {
        return ElevationTokens(
            level0 = dto.level0,
            level1 = dto.level1,
            level2 = dto.level2,
            level3 = dto.level3,
            level4 = dto.level4,
            level5 = dto.level5
        )
    }

    private fun mapIcons(dto: IconsDto): IconTokens {
        val style = when (dto.materialSymbolsStyle?.lowercase()) {
            "filled" -> IconStyle.FILLED
            "rounded" -> IconStyle.ROUNDED
            else -> IconStyle.OUTLINED
        }
        return IconTokens(
            materialSymbolsStyle = style,
            defaultWeight = dto.defaultWeight,
            defaultGrade = dto.defaultGrade,
            defaultOpticalSize = dto.defaultOpticalSize
        )
    }

    private fun mapMeta(dto: MetaDto): ThemeMeta {
        return ThemeMeta(
            generatedBy = dto.generatedBy,
            updatedAt = dto.updatedAt
        )
    }

    private fun String.toColor(): Color {
        val hex = replace("#", "")
        val colorLong = hex.toLong(16)
        return if (hex.length == 6) {
            Color(colorLong or 0x00000000FF000000)
        } else {
            Color(colorLong.toInt())
        }
    }
}