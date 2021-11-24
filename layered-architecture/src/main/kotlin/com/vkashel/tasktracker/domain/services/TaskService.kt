package com.vkashel.tasktracker.domain.services

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.repository.api.TaskRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class TaskService(
    private val userService: UserService,
    private val taskRepository: TaskRepository
) {
    fun createNewTask(
        title: String,
        description: String,
        creator: User,
        assigneeId: Long?
    ): Task {
        val assignee = assigneeId?.let { userService.getById(it) }
        val task = Task(
            title = title,
            description = description,
            createdBy = creator,
            assignee = assignee,
            status = TaskStatus.NEW,
            createdTime = ZonedDateTime.now()
        )
        return taskRepository.save(task)
    }
}