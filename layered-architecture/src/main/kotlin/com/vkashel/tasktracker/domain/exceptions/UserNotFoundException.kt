package com.vkashel.tasktracker.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException : RuntimeException {
    constructor(userId: Long) : super("User was not found by id: $userId")
    constructor(email: String) : super("User was not found by email: $email")
}
