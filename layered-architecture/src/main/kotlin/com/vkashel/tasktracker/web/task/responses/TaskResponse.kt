package com.vkashel.tasktracker.web.task.responses

import com.vkashel.tasktracker.domain.entities.task.Task

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String,
    val creator: Long,
    val assignee: Long?,
    val status: String
) {
    companion object {
        fun of(task: Task): TaskResponse {
            return TaskResponse(
                id = task.id,
                title = task.title,
                description = task.description,
                creator = task.createdBy.id,
                assignee = task.assignee?.id,
                status = task.status.name
            )
        }
    }
}
