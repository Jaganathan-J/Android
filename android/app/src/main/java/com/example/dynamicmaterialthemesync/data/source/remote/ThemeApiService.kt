package com.example.dynamicmaterialthemesync.data.source.remote

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Url

@JsonClass(generateAdapter = true)
data class ThemeJsonResponse(
    val schemaVersion: String,
    val themeVersion: String?,
    val name: String?,
    val colors: ColorsJson?,
    val typography: TypographyJson?,
    val shapes: ShapesJson?,
    val elevation: ElevationJson?,
    val icons: IconsJson?,
    val meta: MetaJson?
)

@JsonClass(generateAdapter = true)
data class ColorsJson(
    val light: ColorSchemeJson?,
    val dark: ColorSchemeJson?
)

@JsonClass(generateAdapter = true)
data class ColorSchemeJson(
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
data class TypographyJson(
    val fontFamily: String?,
    val fallback: List<String>?,
    val weightMapping: Map<String, Int>?,
    val sizeSp: Map<String, Int>?,
    val lineHeightSp: Map<String, Int>?,
    val letterSpacingEm: Map<String, Float>?
)

@JsonClass(generateAdapter = true)
data class ShapesJson(
    val cornerFamily: String?,
    val extraSmall: Int?,
    val small: Int?,
    val medium: Int?,
    val large: Int?,
    val extraLarge: Int?
)

@JsonClass(generateAdapter = true)
data class ElevationJson(
    val level0: Int?,
    val level1: Int?,
    val level2: Int?,
    val level3: Int?,
    val level4: Int?,
    val level5: Int?
)

@JsonClass(generateAdapter = true)
data class IconsJson(
    val materialSymbolsStyle: String?,
    val defaultWeight: Int?,
    val defaultGrade: Int?,
    val defaultOpticalSize: Int?
)

@JsonClass(generateAdapter = true)
data class MetaJson(
    val generatedBy: String?,
    val updatedAt: String?
)

interface ThemeApiService {
    @GET
    suspend fun fetchTheme(@Url url: String): ThemeJsonResponse
}