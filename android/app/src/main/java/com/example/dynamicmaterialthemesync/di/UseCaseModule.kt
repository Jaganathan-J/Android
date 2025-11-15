package com.example.dynamicmaterialthemesync.di

import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import com.example.dynamicmaterialthemesync.domain.usecase.ApplyThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.BuildMaterialThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ObserveThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ResetThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.SyncThemeUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ToggleDynamicColorUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateIconStyleUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.UpdateTypographyUseCase
import com.example.dynamicmaterialthemesync.domain.usecase.ValidateThemeJsonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideBuildMaterialThemeUseCase(
        dynamicColorProvider: DynamicColorProvider
    ): BuildMaterialThemeUseCase = BuildMaterialThemeUseCase(dynamicColorProvider)

    @Provides
    @Singleton
    fun provideObserveThemeUseCase(
        themeRepository: ThemeRepository,
        preferencesRepository: PreferencesRepository,
        fontRepository: FontRepository,
        iconRepository: IconRepository,
        dynamicColorProvider: DynamicColorProvider,
        buildMaterialThemeUseCase: BuildMaterialThemeUseCase
    ): ObserveThemeUseCase = ObserveThemeUseCase(
        themeRepository,
        preferencesRepository,
        fontRepository,
        iconRepository,
        dynamicColorProvider,
        buildMaterialThemeUseCase
    )

    @Provides
    @Singleton
    fun provideValidateThemeJsonUseCase(): ValidateThemeJsonUseCase = ValidateThemeJsonUseCase()

    @Provides
    @Singleton
    fun provideSyncThemeUseCase(
        themeRepository: ThemeRepository,
        validateThemeJsonUseCase: ValidateThemeJsonUseCase
    ): SyncThemeUseCase = SyncThemeUseCase(themeRepository, validateThemeJsonUseCase)

    @Provides
    @Singleton
    fun provideApplyThemeUseCase(
        preferencesRepository: PreferencesRepository
    ): ApplyThemeUseCase = ApplyThemeUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideToggleDynamicColorUseCase(
        preferencesRepository: PreferencesRepository
    ): ToggleDynamicColorUseCase = ToggleDynamicColorUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideUpdateTypographyUseCase(
        preferencesRepository: PreferencesRepository,
        fontRepository: FontRepository
    ): UpdateTypographyUseCase = UpdateTypographyUseCase(preferencesRepository, fontRepository)

    @Provides
    @Singleton
    fun provideUpdateIconStyleUseCase(
        iconRepository: IconRepository
    ): UpdateIconStyleUseCase = UpdateIconStyleUseCase(iconRepository)

    @Provides
    @Singleton
    fun provideResetThemeUseCase(
        preferencesRepository: PreferencesRepository
    ): ResetThemeUseCase = ResetThemeUseCase(preferencesRepository)
}