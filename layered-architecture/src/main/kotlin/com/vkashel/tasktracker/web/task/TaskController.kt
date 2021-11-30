package com.vkashel.tasktracker.web.task

import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.domain.services.TaskService
import com.vkashel.tasktracker.util.PageResponse
import com.vkashel.tasktracker.web.task.requests.CreateTaskRequest
import com.vkashel.tasktracker.web.task.requests.UpdateTaskRequest
import com.vkashel.tasktracker.web.task.responses.TaskResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: CreateTaskRequest, @AuthenticationPrincipal user: User): TaskResponse {
        val task = taskService.createNewTask(
            title = request.title,
            description = request.description,
            creator = user,
            assigneeId = request.assignee
        )
        return TaskResponse.of(task)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateTaskRequest,
        @AuthenticationPrincipal user: User
    ): TaskResponse {
        val updatedTask = taskService.update(
            id = id,
            title = request.title,
            description = request.description,
            newStatus = TaskStatus.valueOf(request.newStatus)
        )
        return TaskResponse.of(updatedTask)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): TaskResponse {
        val task = taskService.getById(id)
        return TaskResponse.of(task)
    }

    @GetMapping
    fun getAll(@RequestParam page: Int = 0, @RequestParam size: Int = 10): PageResponse<TaskResponse> {
        return taskService.findAllPageable(page, size)
            .map { TaskResponse.of(it) }
    }
}
