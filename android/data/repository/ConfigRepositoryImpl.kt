package com.example.material3showcase.data.repository

import com.example.material3showcase.data.remote.api.ConfigApi
import com.example.material3showcase.data.remote.dto.toDomain
import com.example.material3showcase.domain.model.ButtonConfig
import com.example.material3showcase.domain.repository.ConfigRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val api: ConfigApi
) : ConfigRepository {

    override suspend fun fetchButtonConfigs(): Result<List<ButtonConfig>> {
        return safeCall {
            val response = api.getButtonsConfig()
            val mapped = response.map { it.toDomain() }
            if (mapped.isEmpty()) {
                defaultButtons()
            } else {
                mapped
            }
        }
    }

    private suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun defaultButtons(): List<ButtonConfig> = listOf(
        ButtonConfig(label = "Continue", style = com.example.material3showcase.domain.model.ButtonStyle.FILLED, enabled = true, icon = null),
        ButtonConfig(label = "Cancel", style = com.example.material3showcase.domain.model.ButtonStyle.TEXT, enabled = false, icon = null)
    )
}
