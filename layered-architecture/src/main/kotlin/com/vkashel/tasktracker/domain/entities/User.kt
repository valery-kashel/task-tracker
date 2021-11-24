package com.vkashel.tasktracker.domain.entities

import java.time.ZonedDateTime

data class User(
    val id: Long = -1,
    val email: String,
    val password: String,
    val role: UserRole,
    val createdTime: ZonedDateTime
)
