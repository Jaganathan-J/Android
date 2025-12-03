package com.example.automationoverflow.data.repository

import com.example.automationoverflow.domain.model.*
import com.example.automationoverflow.domain.repository.WorkflowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeWorkflowRepository @Inject constructor() : WorkflowRepository {

    private val triggers = listOf(
        Trigger("t1", "Receive Email", "When a new email arrives in Gmail", "gmail"),
        Trigger("t2", "Form Submission", "When a Typeform is submitted", "typeform"),
        Trigger("t3", "New File", "When a file is uploaded to Drive", "drive"),
        Trigger("t4", "Schedule Reminder", "Every day at 9:00 AM", "calendar")
    )

    private val actions = listOf(
        Action("a1", "Send Slack Message", "Post a message to #general", "slack"),
        Action("a2", "Create Document", "Create a new Google Doc", "docs"),
        Action("a3", "Log to Sheet", "Add a row to Google Sheets", "sheets"),
        Action("a4", "Send Push Notification", "Notify me via app", "push")
    )

    private val history = listOf(
        HistoryItem("h1", "Daily Summary Email", "Success", System.currentTimeMillis() - 100000),
        HistoryItem("h2", "Client Form Sync", "Running", System.currentTimeMillis() - 5000),
        HistoryItem("h3", "Backup Finance Docs", "Failed", System.currentTimeMillis() - 86400000),
        HistoryItem("h4", "Morning Brief", "Success", System.currentTimeMillis() - 172800000)
    )

    override suspend fun getTriggers(): List<Trigger> {
        delay(500) // Simulating network
        return triggers
    }

    override suspend fun getActions(): List<Action> {
        delay(500)
        return actions
    }

    override suspend fun createWorkflow(name: String, trigger: Trigger, action: Action): Boolean {
        delay(1500)
        return true
    }

    override fun getHistory(): Flow<List<HistoryItem>> = flow {
        emit(history)
        delay(2000)
        // simulate update
        emit(listOf(HistoryItem("h0", "Just Now Sync", "Running", System.currentTimeMillis())) + history)
    }

    override suspend fun getUserProfile(): User {
        delay(300)
        return User("u1", "Alex", "alex@example.com")
    }
}