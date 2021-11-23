package com.vkashel.tasktracker.repository.datajpa.dto

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
class DtoUser(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column
    var email: String,
    @Column
    var password: String,
    @Column
    var role: String,
    @Column
    var createdTime: ZonedDateTime
)