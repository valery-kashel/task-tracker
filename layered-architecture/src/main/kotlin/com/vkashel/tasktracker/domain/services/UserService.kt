package com.vkashel.tasktracker.domain.services

import com.vkashel.tasktracker.domain.entities.User
import com.vkashel.tasktracker.domain.exceptions.DuplicateEmailException
import com.vkashel.tasktracker.repository.api.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun registerUser(user: User) {
        val existedUser = userRepository.findByEmail(user.email)
        if (existedUser != null) {
            throw DuplicateEmailException("Email already exists")
        }
        userRepository.create(user)
    }
}