package com.vkashel.tasktracker

import com.vkashel.tasktracker.utilservices.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [JwtProperties::class])
class TaskTrackerApplication

fun main(args: Array<String>) {
    runApplication<TaskTrackerApplication>(*args)
}
