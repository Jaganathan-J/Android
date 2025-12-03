package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
}

interface AutomationRepository {
    suspend fun getTriggers(): List<Trigger>
    suspend fun getActions(): List<ActionOption>
    suspend fun createWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean>
    fun getWorkflows(): Flow<List<Workflow>>
    suspend fun getHistory(): List<ExecutionLog>
}