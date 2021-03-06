package com.vkashel.tasktracker.repository.api

import com.vkashel.tasktracker.domain.entities.user.User

interface UserRepository {
    fun create(user: User): User
    fun update(user: User): User
    fun deleteAll()

    fun findById(id: Long): User?
    fun findByEmail(email: String): User?

    fun getByEmail(email: String): User
}
