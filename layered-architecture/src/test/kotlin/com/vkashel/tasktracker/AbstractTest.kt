package com.vkashel.tasktracker

import com.fasterxml.jackson.databind.ObjectMapper
import com.vkashel.tasktracker.domain.entities.task.Task
import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.repository.api.UserRepository
import com.vkashel.tasktracker.utilservices.jwt.JwtTokenProvider
import com.vkashel.tasktracker.web.auth.requests.UserRegistrationRequest
import com.vkashel.tasktracker.web.task.requests.CreateTaskRequest
import com.vkashel.tasktracker.web.task.responses.TaskResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.nio.charset.Charset
import java.util.Random

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.properties"])
@AutoConfigureMockMvc
class AbstractTest {
    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    protected lateinit var mapper: ObjectMapper

    @AfterEach
    private fun clearDb() {
        taskRepository.deleteAll()
        userRepository.deleteAll()
    }

    protected fun createUser(email: String, password: String): User {
        val request = UserRegistrationRequest(
            email = email,
            password = password
        )
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
        return userRepository.getByEmail(email)
    }

    protected fun createToken(user: User): String {
        return tokenProvider.createJwtToken(user)
    }

    protected fun createTask(user: User, assignee: User?): Task {
        val token = createToken(user)

        val taskRequest = CreateTaskRequest(
            title = randomString(10),
            description = randomString(20),
            creator = user.id,
            assignee = assignee?.id
        )

        val resultContent = mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .authorized(token)
                .content(mapper.writeValueAsString(taskRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn().response.contentAsString

        val taskResponse = mapper.readValue(resultContent, TaskResponse::class.java)
        return taskRepository.findById(taskResponse.id)!!
    }

    protected fun randomString(size: Int): String {
        val bytesArray = ByteArray(size)
        val random = Random()
        for (i in 0 until size) {
            bytesArray[i] = (random.nextInt(123 - 97) + 97).toByte()
        }
        return String(bytesArray, Charset.defaultCharset())
    }

    protected fun MockHttpServletRequestBuilder.authorized(token: String): MockHttpServletRequestBuilder {
        return this.header("Authorization", "Bearer $token")
    }
}
