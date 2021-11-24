package com.vkashel.tasktracker.repository.api

import com.vkashel.tasktracker.domain.entities.task.Task

interface TaskRepository {
    fun save(task: Task): Task
    fun deleteAll()

    fun getById(id: Long): Task
}