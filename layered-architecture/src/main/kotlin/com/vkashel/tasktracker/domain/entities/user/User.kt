package com.vkashel.tasktracker.domain.entities.user

import java.time.ZonedDateTime

class User(
    val id: Long = -1,
    val email: String,
    val password: String,
    val role: UserRole,
    val createdTime: ZonedDateTime
)
