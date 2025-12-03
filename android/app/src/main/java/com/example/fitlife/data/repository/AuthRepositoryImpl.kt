package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    // Mock implementation for demo
    private var currentUser: User? = null

    override suspend fun login(email: String, pass: String): Result<User> {
        delay(1000) // Sim network
        return if (email.isNotEmpty() && pass.length >= 6) {
            val user = User("u1", email, "Alex Dev", null)
            currentUser = user
            Result.success(user)
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }

    override suspend fun getCurrentUser(): User? = currentUser

    override suspend fun logout() {
        currentUser = null
    }
}