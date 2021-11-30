package com.vkashel.tasktracker.repository.datajpa.dto

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tasks")
class DtoTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column
    var title: String,
    @Column
    var description: String,

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    var createdBy: DtoUser,

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    var assignee: DtoUser?,

    @Column
    var status: String,

    @Column
    var createdTime: ZonedDateTime
)
