package com.fitlife.automation.domain.repository

import com.fitlife.automation.domain.model.Action
import com.fitlife.automation.domain.model.Trigger
import com.fitlife.automation.domain.model.User
import com.fitlife.automation.domain.model.Workflow
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<User>
    fun getUser(): Flow<User?>
    suspend fun logout()
}

interface WorkflowRepository {
    suspend fun getTriggers(): List<Trigger>
    suspend fun getActions(): List<Action>
    suspend fun createWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean>
    fun getHistory(): Flow<List<Workflow>>
}