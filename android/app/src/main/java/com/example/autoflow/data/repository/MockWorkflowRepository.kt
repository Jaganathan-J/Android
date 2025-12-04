package com.example.autoflow.data.repository

import com.example.autoflow.domain.model.ExecutionLog
import com.example.autoflow.domain.model.ExecutionStatus
import com.example.autoflow.domain.model.ServiceItem
import com.example.autoflow.domain.model.Workflow
import com.example.autoflow.domain.repository.WorkflowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockWorkflowRepository : WorkflowRepository {

    private val _history = MutableStateFlow<List<ExecutionLog>>(
        listOf(
            ExecutionLog("1", "Email to Slack", ExecutionStatus.SUCCESS, System.currentTimeMillis() - 100000),
            ExecutionLog("2", "Form to Sheet", ExecutionStatus.SUCCESS, System.currentTimeMillis() - 200000),
            ExecutionLog("3", "Daily Reminder", ExecutionStatus.FAILED, System.currentTimeMillis() - 86400000)
        )
    )

    private val triggers = listOf(
        ServiceItem("t1", "Receive Email", "When a new email arrives", "Email"),
        ServiceItem("t2", "New Form Response", "When a form is submitted", "Description"),
        ServiceItem("t3", "Schedule", "Every day at 9 AM", "Schedule")
    )

    private val actions = listOf(
        ServiceItem("a1", "Send Slack Message", "Post to #general", "Chat"),
        ServiceItem("a2", "Save to Drive", "Upload file to folder", "CloudQueue"),
        ServiceItem("a3", "Create Task", "Add to Jira/Asana", "CheckBox")
    )

    override suspend fun getTriggers(): List<ServiceItem> {
        delay(500) // Simulate network
        return triggers
    }

    override suspend fun getActions(): List<ServiceItem> {
        delay(500)
        return actions
    }

    override suspend fun saveWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean> {
        delay(1000)
        return Result.success(true)
    }

    override fun getExecutionHistory(): Flow<List<ExecutionLog>> = _history.asStateFlow()

    override suspend fun toggleWorkflow(id: String, active: Boolean) {
        // Mock implementation
    }
}