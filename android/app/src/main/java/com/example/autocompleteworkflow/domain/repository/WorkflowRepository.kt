package com.example.autocompleteworkflow.domain.repository

import com.example.autocompleteworkflow.domain.model.Integration
import com.example.autocompleteworkflow.domain.model.Workflow
import kotlinx.coroutines.flow.Flow

interface WorkflowRepository {
    fun getTriggers(): Flow<List<Integration>>
    fun getActions(triggerId: String): Flow<List<Integration>>
    fun getWorkflows(): Flow<List<Workflow>>
    suspend fun createWorkflow(name: String, trigger: Integration, action: Integration)
    suspend fun toggleWorkflow(id: String, enabled: Boolean)
}