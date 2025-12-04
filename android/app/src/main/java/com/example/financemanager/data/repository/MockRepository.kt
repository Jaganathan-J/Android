package com.example.financemanager.data.repository

import com.example.financemanager.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository @Inject constructor() {

    private val _workflows = MutableStateFlow<List<Workflow>>(emptyList())
    val workflows = _workflows.asStateFlow()

    private val _history = MutableStateFlow<List<ExecutionLog>>(
        listOf(
            ExecutionLog("1", "Daily Invoice Sync", ExecutionStatus.SUCCESS, "Today, 10:00 AM", "Synced 5 invoices"),
            ExecutionLog("2", "Receipt Upload", ExecutionStatus.FAILED, "Yesterday, 4:30 PM", "Network timeout"),
            ExecutionLog("3", "Slack Alert", ExecutionStatus.SUCCESS, "Oct 25, 2:00 PM", "Sent to #finance")
        )
    )
    val history = _history.asStateFlow()

    suspend fun getTriggers(): List<IntegrationItem> {
        delay(500) // Simulate network
        return listOf(
            IntegrationItem("t1", "Gmail", IntegrationType.TRIGGER, "email"),
            IntegrationItem("t2", "Google Forms", IntegrationType.TRIGGER, "form"),
            IntegrationItem("t3", "Webhook", IntegrationType.TRIGGER, "code")
        )
    }

    suspend fun getActions(): List<IntegrationItem> {
        delay(500)
        return listOf(
            IntegrationItem("a1", "Slack", IntegrationType.ACTION, "slack"),
            IntegrationItem("a2", "Google Drive", IntegrationType.ACTION, "drive"),
            IntegrationItem("a3", "Outlook", IntegrationType.ACTION, "email")
        )
    }

    suspend fun saveWorkflow(name: String, trigger: IntegrationItem, action: IntegrationItem) {
        delay(1000)
        val newWorkflow = Workflow(
            id = System.currentTimeMillis().toString(),
            name = name,
            trigger = trigger,
            action = action,
            createdAt = System.currentTimeMillis()
        )
        _workflows.value += newWorkflow
        
        // Add a mock execution log for this new workflow
        val newLog = ExecutionLog(
            id = System.currentTimeMillis().toString(),
            workflowName = name,
            status = ExecutionStatus.SUCCESS,
            timestamp = "Just now",
            message = "Workflow created successfully"
        )
        _history.value = listOf(newLog) + _history.value
    }
}