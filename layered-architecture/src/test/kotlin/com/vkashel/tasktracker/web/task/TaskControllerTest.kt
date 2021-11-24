package com.vkashel.tasktracker.web.task

import com.vkashel.tasktracker.AbstractTest
import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.web.task.requests.CreateTaskRequest
import com.vkashel.tasktracker.web.task.responses.TaskResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TaskControllerTest : AbstractTest() {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Test
    fun givenExistedUser_whenCreateTask_thenCreatedSuccess() {
        val user = createUser("test@gmail.com", "password")
        val assignee = createUser("test2@gmail.com", "password")
        val token = createToken(user)

        val taskRequest = CreateTaskRequest(
            title = "Test title",
            description = "super description",
            creator = user.id,
            assignee = assignee.id
        )

        val resultContent = mvc.perform(
            post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .authorized(token)
                .content(mapper.writeValueAsString(taskRequest))
        )
            .andExpect(status().isCreated)
            .andReturn().response.contentAsString

        val taskResponse = mapper.readValue(resultContent, TaskResponse::class.java)

        assertNotNull(taskResponse.id)
        assertEquals(taskRequest.title, taskResponse.title)
        assertEquals(taskRequest.description, taskResponse.description)
        assertEquals(taskRequest.creator, taskResponse.creator)
        assertEquals(taskRequest.assignee, taskResponse.assignee)

        val task = taskRepository.getById(taskResponse.id)

        assertEquals(taskRequest.title, task.title)
        assertEquals(taskRequest.description, task.description)
        assertEquals(taskRequest.creator, task.createdBy.id)
        assertEquals(taskRequest.assignee, task.assignee?.id)
        assertEquals(TaskStatus.NEW, task.status)
        assertNotNull(task.createdTime)
    }
}