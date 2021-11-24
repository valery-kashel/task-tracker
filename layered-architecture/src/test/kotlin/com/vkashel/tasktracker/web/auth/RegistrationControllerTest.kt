package com.vkashel.tasktracker.web.auth

import com.vkashel.tasktracker.AbstractTest
import com.vkashel.tasktracker.domain.entities.UserRole
import com.vkashel.tasktracker.repository.api.UserRepository
import com.vkashel.tasktracker.web.auth.requests.UserRegistrationRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RegistrationControllerTest : AbstractTest() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun givenNewUser_whenUserRegister_thenRegistrationSuccess() {
        val request = UserRegistrationRequest(
            email = "test@gmail.com",
            password = "password"
        )
        mvc.perform(
            post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)

        val user = userRepository.findByEmail(request.email)
        assertNotNull(user)
        assertEquals(request.email, user!!.email)
        assertTrue(passwordEncoder.matches(request.password, user.password))
        assertEquals(UserRole.USER, user.role)
        assertNotNull(user.createdTime)
        assertNotNull(user.id)
    }

    @Test
    fun givenNewUserWithWrongData_whenUserRegister_thenRegistrationFailedWith400Code() {
        var request = UserRegistrationRequest(
            email = "",
            password = "password"
        )

        mvc.perform(
            post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
        request = UserRegistrationRequest(
            email = "test@gmail.com",
            password = ""
        )

        mvc.perform(
            post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
        assertNull(userRepository.findByEmail(request.email))
    }

    @Test
    fun givenExistedUser_whenNewUserRegisterWithSameEmail_thenRegistrationFailed() {
        val request = UserRegistrationRequest(
            email = "test@gmail.com",
            password = "qwerty"
        )
        mvc.perform(
            post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)

        mvc.perform(
            post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }
}
