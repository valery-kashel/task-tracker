package com.vkashel.tasktracker.repository.datajpa.jparepositories

import com.vkashel.tasktracker.repository.datajpa.dto.DtoUser
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaUserRepository : JpaRepository<DtoUser, Long> {
    fun findByEmail(email: String): DtoUser?
}