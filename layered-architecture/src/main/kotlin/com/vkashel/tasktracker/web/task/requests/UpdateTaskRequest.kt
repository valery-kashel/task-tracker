package com.vkashel.tasktracker.web.task.requests

data class UpdateTaskRequest(
    val title: String,
    val description: String,
    val newStatus: String
)
