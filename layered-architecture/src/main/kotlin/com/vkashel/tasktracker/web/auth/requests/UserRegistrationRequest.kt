package com.vkashel.tasktracker.web.auth.requests

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class UserRegistrationRequest(
    @NotBlank
    @Email
    val email:String,

    @NotBlank
    @Min(6)
    val password: String
)
