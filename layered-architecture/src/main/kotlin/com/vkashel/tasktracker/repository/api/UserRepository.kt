package com.vkashel.tasktracker.repository.api

import com.vkashel.tasktracker.domain.entities.User

interface UserRepository {
    fun create(user: User): User
    fun update(user: User): User
    fun find(id: Long): User?
    fun findByEmail(email: String): User?
}