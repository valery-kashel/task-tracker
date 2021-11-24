package com.vkashel.tasktracker.repository.datajpa.mappingutils

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.repository.datajpa.dto.DtoTask
import com.vkashel.tasktracker.repository.datajpa.dto.DtoUser

internal fun Task.toDto(createdBy: DtoUser, assignee: DtoUser? = null): DtoTask {
    return DtoTask(
        id = this.id,
        title = this.title,
        description = this.description,
        createdBy = createdBy,
        assignee = assignee,
        status = this.status.name,
        createdTime = this.createdTime
    )
}

internal fun DtoTask.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        createdBy = this.createdBy.toUser(),
        assignee = this.assignee?.toUser(),
        status = TaskStatus.valueOf(this.status),
        createdTime = this.createdTime
    )
}