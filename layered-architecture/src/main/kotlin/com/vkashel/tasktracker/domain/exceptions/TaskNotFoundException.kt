package com.vkashel.tasktracker.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class TaskNotFoundException(val taskId: Long) : RuntimeException("Task was not found with id: $taskId")
