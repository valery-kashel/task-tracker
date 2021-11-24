package com.vkashel.tasktracker.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserNotFoundException(val email: String) : RuntimeException("User was not found by email: $email")
