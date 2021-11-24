package com.vkashel.tasktracker.web.user

import com.vkashel.tasktracker.AbstractTest
import com.vkashel.tasktracker.web.user.responses.UserInfoResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest : AbstractTest() {

    @Test
    fun givenExistedUser_whenReceiveInfo_thenReturnInfoSuccess() {
        val email = "test@gmail.com"
        val password = "password"
        val user = createUser(email, password)
        val token = createToken(user)

        val resultContent = mvc.perform(
            get("/api/v1/users/me")
                .contentType(MediaType(MediaType.APPLICATION_JSON))
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val result = mapper.readValue(resultContent, UserInfoResponse::class.java)
        assertNotNull(result)
        assertEquals(user.id, result.id)
        assertEquals(user.email, result.email)
        assertEquals(user.role.name, result.role)
    }

    @Test
    fun givenExistedUser_whenReceiveInfoWithoutToken_thenReturnError() {
        val email = "test@gmail.com"
        val password = "password"
        createUser(email, password)

        mvc.perform(
            get("/api/v1/users/me")
                .contentType(MediaType(MediaType.APPLICATION_JSON))
                .header("Authorization", "Bearer ")
        )
            .andExpect(status().isForbidden)
    }
}
