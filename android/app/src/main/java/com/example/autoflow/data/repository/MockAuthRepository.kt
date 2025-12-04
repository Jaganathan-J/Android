package com.example.autoflow.data.repository

import com.example.autoflow.domain.model.User
import com.example.autoflow.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class MockAuthRepository : AuthRepository {
    private var currentUser: User? = null

    override suspend fun login(email: String, pass: String): Result<User> {
        delay(1000)
        if (email.contains("@")) {
            val user = User("u1", "Alex", email)
            currentUser = user
            return Result.success(user)
        }
        return Result.failure(Exception("Invalid credentials"))
    }

    override suspend fun getCurrentUser(): User? = currentUser

    override suspend fun logout() {
        currentUser = null
    }
}