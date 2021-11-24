package com.vkashel.tasktracker.repository.datajpa.impl

import com.vkashel.tasktracker.domain.entities.User
import com.vkashel.tasktracker.domain.entities.UserRole
import com.vkashel.tasktracker.repository.api.UserRepository
import com.vkashel.tasktracker.repository.datajpa.dto.DtoUser
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl : UserRepository {

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    override fun create(user: User): User {
        return dataJpaUserRepository.save(user.toDto()).toUser()
    }

    override fun update(user: User): User {
        return dataJpaUserRepository.save(user.toDto()).toUser()
    }

    override fun find(id: Long): User? {
        return dataJpaUserRepository.findById(id)
            .map { it.toUser() }
            .orElse(null)
    }

    override fun findByEmail(email: String): User? {
        return dataJpaUserRepository.findByEmail(email)?.toUser()
    }

    private fun User.toDto(): DtoUser {
        return DtoUser(
            id = this.id,
            email = this.email,
            password = this.password,
            role = this.role.name,
            createdTime = this.createdTime
        )
    }

    private fun DtoUser.toUser(): User {
        return User(
            id = this.id,
            email = this.email,
            password = this.password,
            role = UserRole.valueOf(this.role),
            createdTime = this.createdTime
        )
    }
}