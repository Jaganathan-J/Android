package com.example.autocompleteworkflow.data.repository

import com.example.autocompleteworkflow.domain.model.*
import com.example.autocompleteworkflow.domain.repository.WorkflowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockWorkflowRepositoryImpl @Inject constructor() : WorkflowRepository {

    private val _workflows = MutableStateFlow<List<Workflow>>(listOf(
        Workflow(
            id = "1", 
            name = "Daily Summary Email", 
            trigger = Integration("t1", "Timer", "Every day at 9am", IntegrationType.TRIGGER, "timer"),
            action = Integration("a1", "Email", "Send daily summary", IntegrationType.ACTION, "mail"),
            isEnabled = true,
            lastRunStatus = RunStatus.SUCCESS,
            lastRunTime = "2 hrs ago"
        ),
        Workflow(
            id = "2",
            name = "Slack to Trello",
            trigger = Integration("t2", "Slack", "New saved message", IntegrationType.TRIGGER, "chat"),
            action = Integration("a2", "Trello", "Create card", IntegrationType.ACTION, "trello"),
            isEnabled = false,
            lastRunStatus = RunStatus.NONE,
            lastRunTime = "Never"
        )
    ))

    override fun getTriggers(): Flow<List<Integration>> = flow {
        delay(500) // Sim network
        emit(listOf(
            Integration("t1", "Context Timer", "Triggers every day at specific time", IntegrationType.TRIGGER, "timer"),
            Integration("t2", "Slack", "When a new message is posted", IntegrationType.TRIGGER, "chat"),
            Integration("t3", "Gmail", "New email received from specific sender", IntegrationType.TRIGGER, "mail"),
            Integration("t4", "Calendar", "15 minutes before event", IntegrationType.TRIGGER, "calendar")
        ))
    }

    override fun getActions(triggerId: String): Flow<List<Integration>> = flow {
        delay(500)
        emit(listOf(
            Integration("a1", "Send Email", "Send an email via Gmail", IntegrationType.ACTION, "mail"),
            Integration("a2", "Create Card", "Create a card on Trello", IntegrationType.ACTION, "trello"),
            Integration("a3", "Send Slack Msg", "Send a message to a channel", IntegrationType.ACTION, "chat"),
            Integration("a4", "Generate Doc", "Create a Google Doc", IntegrationType.ACTION, "doc")
        ))
    }

    override fun getWorkflows(): Flow<List<Workflow>> = _workflows.asStateFlow()

    override suspend fun createWorkflow(name: String, trigger: Integration, action: Integration) {
        delay(1000)
        val newFlow = Workflow(
            id = UUID.randomUUID().toString(),
            name = name,
            trigger = trigger,
            action = action,
            isEnabled = true,
            lastRunStatus = RunStatus.NONE,
            lastRunTime = "Just now"
        )
        _workflows.value = listOf(newFlow) + _workflows.value
    }

    override suspend fun toggleWorkflow(id: String, enabled: Boolean) {
        val currentList = _workflows.value.map {
            if (it.id == id) it.copy(isEnabled = enabled) else it
        }
        _workflows.value = currentList
    }
}