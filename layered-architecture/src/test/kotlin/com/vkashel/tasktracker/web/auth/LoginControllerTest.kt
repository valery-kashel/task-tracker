package com.vkashel.tasktracker.web.auth

import com.vkashel.tasktracker.AbstractTest
import com.vkashel.tasktracker.utilservices.jwt.JwtTokenProvider
import com.vkashel.tasktracker.web.auth.requests.LoginRequest
import com.vkashel.tasktracker.web.auth.responses.TokenResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LoginControllerTest : AbstractTest() {

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Test
    fun givenExistedUser_whenLogin_thenReturnToken() {
        val email = "test@gmail.com"
        val password = "password"
        val user = createUser(email, password)
        val loginRequest = LoginRequest(
            email = email,
            password = password
        )
        val responseContent = mvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val tokenResponse = mapper.readValue(responseContent, TokenResponse::class.java)
        val userId = tokenProvider.verifyTokenAndGetUserId(tokenResponse.token)

        assertEquals(user.id, userId)
    }

    @Test
    fun givenExistedUser_whenLoginWithWrongCredentials_thenReturnError() {
        val email = "test@gmail.com"
        val password = "password"
        createUser(email, password)
        val loginRequest = LoginRequest(
            email = email,
            password = "wrongPassword"
        )
        mvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        )
            .andExpect(status().isUnauthorized)
    }
}
