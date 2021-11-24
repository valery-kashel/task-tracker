package com.vkashel.tasktracker.web.task

import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.domain.services.TaskService
import com.vkashel.tasktracker.web.task.requests.CreateTaskRequest
import com.vkashel.tasktracker.web.task.responses.TaskResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
}