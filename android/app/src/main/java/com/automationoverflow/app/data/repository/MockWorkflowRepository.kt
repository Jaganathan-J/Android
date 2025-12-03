package com.automationoverflow.app.data.repository

import com.automationoverflow.app.domain.models.ActionOption
import com.automationoverflow.app.domain.models.TriggerOption
import com.automationoverflow.app.domain.models.WorkflowItem
import com.automationoverflow.app.domain.repository.WorkflowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockWorkflowRepository @Inject constructor() : WorkflowRepository {

    private val history = mutableListOf(
        WorkflowItem("1", "Daily Summary", "Receive Email", "Slack Msg", "Success", "10:00 AM"),
        WorkflowItem("2", "New Lead", "Form Submit", "Add Row", "Running", "10:05 AM")
    )

    override suspend fun getTriggers(): List<TriggerOption> {
        delay(500)
        return listOf(
            TriggerOption("t1", "Receive Email", "Recent...", "email"),
            TriggerOption("t2", "Form Submission", "New entry...", "form"),
            TriggerOption("t3", "Time of Day", "Every morning", "clock")
        )
    }

    override suspend fun getActions(): List<ActionOption> {
        delay(500)
        return listOf(
            ActionOption("a1", "Send Slack Message", "#general", "slack"),
            ActionOption("a2", "Create Cal Event", "Add to calendar", "calendar"),
            ActionOption("a3", "Generate Document", "From template", "doc")
        )
    }

    override suspend fun createWorkflow(name: String, triggerId: String, actionId: String) {
        delay(1000)
        // Simulate success
        history.add(0, WorkflowItem(
            id = System.currentTimeMillis().toString(),
            name = name,
            triggerName = "Trigger $triggerId",
            actionName = "Action $actionId",
            status = "Success",
            timestamp = "Now"
        ))
    }

    override fun getExecutionHistory(): Flow<List<WorkflowItem>> = flow {
        emit(history)
    }
}