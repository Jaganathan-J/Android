package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Automation
import com.example.fitlife.domain.model.ExecutionHistory
import com.example.fitlife.domain.model.IntegrationItem
import kotlinx.coroutines.flow.Flow

interface AutomationRepository {
    fun getTriggers(): List<IntegrationItem>
    fun getActions(): List<IntegrationItem>
    suspend fun saveAutomation(name: String, trigger: IntegrationItem, action: IntegrationItem)
    fun getHistory(): Flow<List<ExecutionHistory>>
    suspend fun toggleAutomation(id: String, active: Boolean)
}