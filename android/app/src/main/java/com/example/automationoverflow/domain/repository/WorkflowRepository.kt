package com.example.automationoverflow.domain.repository

import com.example.automationoverflow.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WorkflowRepository {
    suspend fun getTriggers(): List<Trigger>
    suspend fun getActions(): List<Action>
    suspend fun createWorkflow(name: String, trigger: Trigger, action: Action): Boolean
    fun getHistory(): Flow<List<HistoryItem>>
    suspend fun getUserProfile(): User
}