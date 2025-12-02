package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.User
import com.example.fitlife.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockAuthRepository : AuthRepository {
    private val _user = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _user

    override suspend fun login(email: String, pass: String): Boolean {
        // Simulating network delay
        kotlinx.coroutines.delay(1000)
        if (pass.isNotEmpty()) {
            _user.value = User("1", "Emma Johnson", email)
            return true
        }
        return false
    }

    override suspend fun logout() {
        _user.value = null
    }
}