package com.vkashel.tasktracker.repository.datajpa.mappingutils

import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.domain.entities.user.UserRole
import com.vkashel.tasktracker.repository.datajpa.dto.DtoUser

internal fun User.toDto(): DtoUser {
    return DtoUser(
        id = this.id,
        email = this.email,
        password = this.password,
        role = this.role.name,
        createdTime = this.createdTime
    )
}

internal fun DtoUser.toUser(): User {
    return User(
        id = this.id,
        email = this.email,
        password = this.password,
        role = UserRole.valueOf(this.role),
        createdTime = this.createdTime
    )
}
