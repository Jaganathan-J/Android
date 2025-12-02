package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun observeUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
}