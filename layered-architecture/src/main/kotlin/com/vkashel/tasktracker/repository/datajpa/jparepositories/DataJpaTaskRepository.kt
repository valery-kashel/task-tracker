package com.vkashel.tasktracker.repository.datajpa.jparepositories

import com.vkashel.tasktracker.repository.datajpa.dto.DtoTask
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaTaskRepository : JpaRepository<DtoTask, Long>
