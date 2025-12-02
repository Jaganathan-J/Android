package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val _user = MutableStateFlow<User?>(null)

    override fun observeUser(): Flow<User?> = _user

    override suspend fun login(email: String, password: String): Result<User> {
        // Simulate network delay
        kotlinx.coroutines.delay(800)
        if (email.isNotEmpty() && password.length >= 4) {
            val user = User("1", "Emma Johnson", email)
            _user.value = user
            return Result.success(user)
        }
        return Result.failure(Exception("Invalid credentials"))
    }

    override suspend fun logout() {
        _user.value = null
    }
}