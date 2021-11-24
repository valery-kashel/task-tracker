package com.vkashel.tasktracker

import com.fasterxml.jackson.databind.ObjectMapper
import com.vkashel.tasktracker.domain.entities.user.User
import com.vkashel.tasktracker.repository.api.TaskRepository
import com.vkashel.tasktracker.repository.api.UserRepository
import com.vkashel.tasktracker.utilservices.jwt.JwtTokenProvider
import com.vkashel.tasktracker.web.auth.requests.UserRegistrationRequest
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

    protected fun MockHttpServletRequestBuilder.authorized(token: String): MockHttpServletRequestBuilder {
        return this.header("Authorization", "Bearer $token")
    }
}
