package com.automationoverflow.app.domain.repository

import com.automationoverflow.app.domain.models.ActionOption
import com.automationoverflow.app.domain.models.TriggerOption
import com.automationoverflow.app.domain.models.WorkflowItem
import kotlinx.coroutines.flow.Flow

interface WorkflowRepository {
    suspend fun getTriggers(): List<TriggerOption>
    suspend fun getActions(): List<ActionOption>
    suspend fun createWorkflow(name: String, triggerId: String, actionId: String)
    fun getExecutionHistory(): Flow<List<WorkflowItem>>
}