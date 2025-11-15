package com.example.material3showcase.data.repository

import com.example.material3showcase.data.mapper.toDomain
import com.example.material3showcase.data.remote.api.ConfigApiService
import com.example.material3showcase.domain.model.AvatarModel
import com.example.material3showcase.domain.model.ButtonModel
import com.example.material3showcase.domain.model.CardModel
import com.example.material3showcase.domain.model.DialogModel
import com.example.material3showcase.domain.model.TextFieldConfig
import com.example.material3showcase.domain.repository.ConfigRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val api: ConfigApiService
) : ConfigRepository {

    override fun getButtonsConfig(): Flow<Result<List<ButtonModel>>> = flow {
        try {
            val remote = api.getButtons()
            val mapped = remote.mapNotNull { it.toDomain() }
            val result = if (mapped.isEmpty()) defaultButtons() else mapped
            emit(Result.success(result))
        } catch (t: Throwable) {
            emit(Result.success(defaultButtons()))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getCardsConfig(): Flow<Result<List<CardModel>>> = flow {
        delay(300)
        emit(Result.success(defaultCards()))
    }.catch { e -> emit(Result.failure(e)) }

    override fun getAvatarsConfig(): Flow<Result<List<AvatarModel>>> = flow {
        delay(300)
        emit(Result.success(defaultAvatars()))
    }.catch { e -> emit(Result.failure(e)) }

    override fun getDialogsConfig(): Flow<Result<List<DialogModel>>> = flow {
        delay(300)
        emit(Result.success(defaultDialogs()))
    }.catch { e -> emit(Result.failure(e)) }

    override fun getTextFieldsConfig(): Flow<Result<List<TextFieldConfig>>> = flow {
        delay(300)
        emit(Result.success(defaultTextFields()))
    }.catch { e -> emit(Result.failure(e)) }

    private fun defaultButtons(): List<ButtonModel> = listOf(
        ButtonModel("Continue", "filled", true, "ic_check"),
        ButtonModel("Cancel", "text", false, null),
        ButtonModel("Outlined", "outlined", true, null),
        ButtonModel("Tonal", "tonal", true, null),
        ButtonModel("Elevated", "elevated", true, null)
    )

    private fun defaultCards(): List<CardModel> = listOf(
        CardModel("1", "Elevated Card", "This is an example of an elevated card."),
        CardModel("2", "Outlined Card", "This is an outlined card.", outlined = true),
        CardModel("3", "Image Card", "Card with a placeholder image.", imageUrl = null)
    )

    private fun defaultAvatars(): List<AvatarModel> = listOf(
        AvatarModel("1", "Alice Johnson", null, hasBadge = true),
        AvatarModel("2", "Bob Stone", null, hasBadge = false),
        AvatarModel("3", "Carlos Vega", null, hasBadge = true)
    )

    private fun defaultDialogs(): List<DialogModel> = listOf(
        DialogModel("1", "Delete item", "Are you sure you want to delete this item?", "Delete", "Cancel"),
        DialogModel("2", "Sign out", "Do you want to sign out from this device?", "Sign out", "Stay")
    )

    private fun defaultTextFields(): List<TextFieldConfig> = listOf(
        TextFieldConfig("1", "Email", "you@example.com", isPassword = false, supportingText = "We'll never share your email"),
        TextFieldConfig("2", "Password", "••••••••", isPassword = true, supportingText = "At least 8 characters"),
        TextFieldConfig("3", "Username", "Choose a unique name", isPassword = false)
    )
}
