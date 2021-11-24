package com.vkashel.tasktracker.web.auth.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserRegistrationRequest(
    @get:NotBlank
    @get:Email
    val email: String,

    @get:NotBlank
    @get:Size(min = 6)
    val password: String
)
