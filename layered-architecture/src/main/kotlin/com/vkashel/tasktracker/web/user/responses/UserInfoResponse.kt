package com.vkashel.tasktracker.web.user.responses

import com.vkashel.tasktracker.domain.entities.User

data class UserInfoResponse(
    val id: Long,
    val email: String,
    val role: String
) {
    companion object {
        fun of(user: User): UserInfoResponse {
            return UserInfoResponse(
                id = user.id,
                email = user.email,
                role = user.role.name
            )
        }
    }
}
