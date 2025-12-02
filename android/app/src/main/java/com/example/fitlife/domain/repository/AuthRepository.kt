package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, pass: String): Boolean
    suspend fun logout()
}