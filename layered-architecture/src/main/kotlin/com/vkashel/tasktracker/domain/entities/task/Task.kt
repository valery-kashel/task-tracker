package com.vkashel.tasktracker.domain.entities.task

import com.vkashel.tasktracker.domain.entities.user.User
import java.time.ZonedDateTime

class Task(
    val id: Long = -1,
    val title: String,
    val description: String,
    val createdBy: User,
    val assignee: User?,
    val status: TaskStatus,
    val createdTime: ZonedDateTime
)