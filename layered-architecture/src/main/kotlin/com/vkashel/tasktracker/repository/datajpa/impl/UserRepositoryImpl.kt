package com.vkashel.tasktracker.repository.datajpa.impl

import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.repository.api.UserRepository
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaUserRepository
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toDto
import com.vkashel.tasktracker.repository.datajpa.mappingutils.toUser
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

    override fun findById(id: Long): User? {
        return dataJpaUserRepository.findById(id)
            .map { it.toUser() }
            .orElse(null)
    }

    override fun findByEmail(email: String): User? {
        return dataJpaUserRepository.findByEmail(email)?.toUser()
    }

    override fun getByEmail(email: String): User {
        return findByEmail(email)!!
    }

    override fun deleteAll() {
        dataJpaUserRepository.deleteAll()
    }
}
