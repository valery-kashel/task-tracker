package com.vkashel.tasktracker.domain.services

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.domain.exceptions.TaskNotFoundException
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.util.PageResponse
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class TaskService(
    private val userService: UserService,
    private val taskRepository: TaskRepository
) {
    fun createNewTask(title: String, description: String, creator: User, assigneeId: Long?): Task {
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

    fun getById(id: Long): Task {
        return taskRepository.findById(id) ?: throw TaskNotFoundException(id)
    }

    fun update(id: Long, title: String, description: String, newStatus: TaskStatus): Task {
        val task = getById(id)
        return taskRepository.save(
            task.copy(
                title = title,
                description = description,
                status = newStatus
            )
        )
    }

    fun findAllPageable(page: Int, size: Int): PageResponse<Task> {
        return taskRepository.findAll(page, size)
    }
}
