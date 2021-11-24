package com.vkashel.tasktracker.web.task.requests

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class CreateTaskRequest(
    @get:NotBlank
    val title: String,
    @get:NotBlank
    val description: String,
    @get:Min(1)
    val creator: Long,
    var assignee: Long? = null
)