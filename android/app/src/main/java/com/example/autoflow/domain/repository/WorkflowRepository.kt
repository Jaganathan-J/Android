package com.example.autoflow.domain.repository

import com.example.autoflow.domain.model.ExecutionLog
import com.example.autoflow.domain.model.ServiceItem
import com.example.autoflow.domain.model.Workflow
import kotlinx.coroutines.flow.Flow

interface WorkflowRepository {
    suspend fun getTriggers(): List<ServiceItem>
    suspend fun getActions(): List<ServiceItem>
    suspend fun saveWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean>
    fun getExecutionHistory(): Flow<List<ExecutionLog>>
    suspend fun toggleWorkflow(id: String, active: Boolean)
}