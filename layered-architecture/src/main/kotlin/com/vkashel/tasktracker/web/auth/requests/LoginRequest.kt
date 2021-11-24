package com.vkashel.tasktracker.web.auth.requests

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequest(
    @get:Email
    @get:NotBlank
    val email: String,

    @get:Length(min = 6)
    val password: String
)
