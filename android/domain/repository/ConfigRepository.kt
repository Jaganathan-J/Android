package com.example.material3showcase.domain.repository

import com.example.material3showcase.domain.model.ButtonModel
import com.example.material3showcase.domain.model.CardModel
import com.example.material3showcase.domain.model.AvatarModel
import com.example.material3showcase.domain.model.DialogModel
import com.example.material3showcase.domain.model.TextFieldConfig
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getButtonsConfig(): Flow<Result<List<ButtonModel>>>
    fun getCardsConfig(): Flow<Result<List<CardModel>>>
    fun getAvatarsConfig(): Flow<Result<List<AvatarModel>>>
    fun getDialogsConfig(): Flow<Result<List<DialogModel>>>
    fun getTextFieldsConfig(): Flow<Result<List<TextFieldConfig>>>
}
