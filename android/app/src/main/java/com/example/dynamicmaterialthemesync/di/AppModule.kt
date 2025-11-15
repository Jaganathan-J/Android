package com.example.dynamicmaterialthemesync.di

import android.content.Context
import com.example.dynamicmaterialthemesync.core.platform.DynamicColorProviderImpl
import com.example.dynamicmaterialthemesync.data.repository.FontRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.IconRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.PreferencesRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.ThemeRepositoryImpl
import com.example.dynamicmaterialthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicmaterialthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeApiService
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalThemeDataSource(@ApplicationContext context: Context): LocalThemeDataSource =
        LocalThemeDataSource(context)

    @Provides
    @Singleton
    fun provideThemePreferencesDataSource(@ApplicationContext context: Context): ThemePreferencesDataSource =
        ThemePreferencesDataSource(context)

    @Provides
    @Singleton
    fun provideThemeRepository(
        apiService: ThemeApiService,
        localThemeDataSource: LocalThemeDataSource
    ): ThemeRepository = ThemeRepositoryImpl(apiService, localThemeDataSource)

    @Provides
    @Singleton
    fun providePreferencesRepository(dataSource: ThemePreferencesDataSource): PreferencesRepository =
        PreferencesRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideFontRepository(): FontRepository = FontRepositoryImpl()

    @Provides
    @Singleton
    fun provideIconRepository(): IconRepository = IconRepositoryImpl()

    @Provides
    @Singleton
    fun provideDynamicColorProvider(@ApplicationContext context: Context): DynamicColorProvider =
        DynamicColorProviderImpl(context)

    @Provides
    @Singleton
    fun provideValidateThemeJsonUseCase(): ValidateThemeJsonUseCase = ValidateThemeJsonUseCase()

    @Provides
    @Singleton
    fun provideBuildMaterialThemeUseCase(): BuildMaterialThemeUseCase = BuildMaterialThemeUseCase()

    @Provides
    @Singleton
    fun provideSyncThemeUseCase(
        themeRepository: ThemeRepository,
        validateThemeJsonUseCase: ValidateThemeJsonUseCase
    ): SyncThemeUseCase = SyncThemeUseCase(themeRepository, validateThemeJsonUseCase)

    @Provides
    @Singleton
    fun provideApplyThemeUseCase(preferencesRepository: PreferencesRepository): ApplyThemeUseCase =
        ApplyThemeUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideToggleDynamicColorUseCase(preferencesRepository: PreferencesRepository): ToggleDynamicColorUseCase =
        ToggleDynamicColorUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideUpdateTypographyUseCase(
        fontRepository: FontRepository,
        preferencesRepository: PreferencesRepository
    ): UpdateTypographyUseCase = UpdateTypographyUseCase(fontRepository, preferencesRepository)

    @Provides
    @Singleton
    fun provideUpdateIconStyleUseCase(iconRepository: IconRepository): UpdateIconStyleUseCase =
        UpdateIconStyleUseCase(iconRepository)

    @Provides
    @Singleton
    fun provideResetThemeUseCase(preferencesRepository: PreferencesRepository): ResetThemeUseCase =
        ResetThemeUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideObserveThemeUseCase(
        themeRepository: ThemeRepository,
        preferencesRepository: PreferencesRepository,
        iconRepository: IconRepository,
        dynamicColorProvider: DynamicColorProvider,
        buildMaterialThemeUseCase: BuildMaterialThemeUseCase
    ): ObserveThemeUseCase = ObserveThemeUseCase(
        themeRepository,
        preferencesRepository,
        iconRepository,
        dynamicColorProvider,
        buildMaterialThemeUseCase
    )
}