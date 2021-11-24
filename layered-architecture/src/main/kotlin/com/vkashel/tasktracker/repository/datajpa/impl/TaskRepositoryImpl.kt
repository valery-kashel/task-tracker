package com.vkashel.tasktracker.repository.datajpa.impl

import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaTaskRepository
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaUserRepository
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toDto
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toTask
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

    override fun getById(id: Long): Task {
        return taskRepository.getById(id).toTask()
    }
}