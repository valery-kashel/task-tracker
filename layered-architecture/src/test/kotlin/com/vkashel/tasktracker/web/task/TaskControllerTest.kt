package com.vkashel.tasktracker.web.task

import com.vkashel.tasktracker.AbstractTest
import com.vkashel.tasktracker.domain.entities.task.TaskStatus
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.web.task.requests.CreateTaskRequest
import com.vkashel.tasktracker.web.task.requests.UpdateTaskRequest
import com.vkashel.tasktracker.web.task.responses.TaskResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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

        val task = taskRepository.findById(taskResponse.id)!!

        assertEquals(taskRequest.title, task.title)
        assertEquals(taskRequest.description, task.description)
        assertEquals(taskRequest.creator, task.createdBy.id)
        assertEquals(taskRequest.assignee, task.assignee?.id)
        assertEquals(TaskStatus.NEW, task.status)
        assertNotNull(task.createdTime)
    }

    @Test
    fun givenExistingTask_whenGetById_thenReturnSuccess() {
        val user = createUser("test@gmail.com", "password")
        val assignee = createUser("test2@gmail.com", "password")
        val token = createToken(user)
        val task = createTask(user, assignee)

        val resultContent = mvc.perform(
            get("/api/v1/tasks/${task.id}")
                .authorized(token)
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val result = mapper.readValue(resultContent, TaskResponse::class.java)

        assertEquals(task.id, result.id)
        assertEquals(task.title, result.title)
        assertEquals(task.description, result.description)
        assertEquals(task.assignee?.id, result.assignee)
        assertEquals(task.createdBy.id, result.creator)
        assertEquals(task.status.name, result.status)
    }

    @Test
    fun givenExistingTask_whenGetByWrongId_thenReturnNotFound() {
        val user = createUser("test@gmail.com", "password")
        val assignee = createUser("test2@gmail.com", "password")
        val token = createToken(user)
        createTask(user, assignee)

        mvc.perform(
            get("/api/v1/tasks/123")
                .authorized(token)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun givenExistingTask_whenUpdate_thenReturnUpdatedTask() {
        val user = createUser("test@gmail.com", "password")
        val assignee = createUser("test2@gmail.com", "password")
        val token = createToken(user)
        val task = createTask(user, assignee)

        val updateTaskRequest = UpdateTaskRequest(
            title = randomString(20),
            description = randomString(40),
            newStatus = TaskStatus.DONE.name
        )

        val responseContent = mvc.perform(
            put("/api/v1/tasks/${task.id}")
                .authorized(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateTaskRequest))
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val result = mapper.readValue(responseContent, TaskResponse::class.java)

        assertEquals(task.id, result.id)
        assertEquals(updateTaskRequest.title, result.title)
        assertEquals(updateTaskRequest.description, result.description)
        assertEquals(updateTaskRequest.newStatus, result.status)
        assertEquals(task.createdBy.id, result.creator)
        assertEquals(task.assignee?.id, result.assignee)
    }

    @Test
    fun givenExistingTask_whenUpdateByWrongId_thenReturnNotFound() {
        val user = createUser("test@gmail.com", "password")
        val assignee = createUser("test2@gmail.com", "password")
        val token = createToken(user)
        createTask(user, assignee)

        val updateTaskRequest = UpdateTaskRequest(
            title = randomString(20),
            description = randomString(40),
            newStatus = TaskStatus.DONE.name
        )

        mvc.perform(
            put("/api/v1/tasks/123")
                .authorized(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateTaskRequest))
        )
            .andExpect(status().isNotFound)
    }
}
