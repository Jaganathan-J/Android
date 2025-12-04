package com.example.autoflow.domain.repository

import com.example.autoflow.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
}