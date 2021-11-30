package com.vkashel.tasktracker.repository.api

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.util.PageResponse

interface TaskRepository {
    fun save(task: Task): Task
    fun deleteAll()

    fun findById(id: Long): Task?
    fun findAll(page: Int, size: Int): PageResponse<Task>
}
