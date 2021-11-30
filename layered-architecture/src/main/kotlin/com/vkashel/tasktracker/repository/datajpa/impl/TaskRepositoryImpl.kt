package com.vkashel.tasktracker.repository.datajpa.impl

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaTaskRepository
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaUserRepository
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toDto
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toTask
import com.vkashel.tasktracker.util.PageResponse
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class TaskRepositoryImpl(
    private val taskRepository: DataJpaTaskRepository,
    private val userRepository: DataJpaUserRepository
) : TaskRepository {
    override fun save(task: Task): Task {
        val createdBy = userRepository.findById(task.createdBy.id).orElse(null)
        val assignee = task.assignee?.id?.let { userRepository.findById(it).orElse(null) }

        val dtoTask = task.toDto(createdBy, assignee)
        return taskRepository.save(dtoTask).toTask()
    }

    override fun deleteAll() {
        taskRepository.deleteAll()
    }

    override fun findById(id: Long): Task? {
        return taskRepository.findById(id)
            .map { it.toTask() }
            .orElse(null)
    }

    override fun findAll(page: Int, size: Int): PageResponse<Task> {
        val pageable = PageRequest.of(page, size)
        val pagedTasks = taskRepository.findAll(pageable)
        return PageResponse.of(pageable, pagedTasks)
            .map { it.toTask() }
    }
}
