package com.example.dynamicmaterialthemesync.data.source.remote

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Url

interface ThemeApiService {
    @GET
    suspend fun fetchTheme(@Url url: String): ThemeJsonDto
}

@JsonClass(generateAdapter = true)
data class ThemeJsonDto(
    val schemaVersion: String,
    val themeVersion: String?,
    val name: String?,
    val colors: ColorsDto?,
    val typography: TypographyDto?,
    val shapes: ShapesDto?,
    val elevation: ElevationDto?,
    val icons: IconsDto?,
    val meta: MetaDto?
)

@JsonClass(generateAdapter = true)
data class ColorsDto(
    val light: ColorRolesDto?,
    val dark: ColorRolesDto?
)

@JsonClass(generateAdapter = true)
data class ColorRolesDto(
    val primary: String?,
    val onPrimary: String?,
    val primaryContainer: String?,
    val onPrimaryContainer: String?,
    val secondary: String?,
    val onSecondary: String?,
    val background: String?,
    val onBackground: String?,
    val surface: String?,
    val onSurface: String?,
    val surfaceVariant: String?,
    val onSurfaceVariant: String?,
    val tertiary: String?,
    val error: String?,
    val onError: String?,
    val outline: String?,
    val inverseSurface: String?,
    val inverseOnSurface: String?
)

@JsonClass(generateAdapter = true)
data class TypographyDto(
    val fontFamily: String?,
    val fallback: List<String>?,
    val weightMapping: Map<String, Int>?,
    val sizeSp: Map<String, Int>?,
    val lineHeightSp: Map<String, Int>?,
    val letterSpacingEm: Map<String, Float>?
)

@JsonClass(generateAdapter = true)
data class ShapesDto(
    val cornerFamily: String?,
    val extraSmall: Int?,
    val small: Int?,
    val medium: Int?,
    val large: Int?,
    val extraLarge: Int?
)

@JsonClass(generateAdapter = true)
data class ElevationDto(
    val level0: Int?,
    val level1: Int?,
    val level2: Int?,
    val level3: Int?,
    val level4: Int?,
    val level5: Int?
)

@JsonClass(generateAdapter = true)
data class IconsDto(
    val materialSymbolsStyle: String?,
    val defaultWeight: Int?,
    val defaultGrade: Int?,
    val defaultOpticalSize: Int?
)

@JsonClass(generateAdapter = true)
data class MetaDto(
    val generatedBy: String?,
    val updatedAt: String?
)