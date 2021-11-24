package com.vkashel.tasktracker.web.auth

import com.vkashel.tasktracker.domain.services.LoginService
import com.vkashel.tasktracker.web.auth.requests.LoginRequest
import com.vkashel.tasktracker.web.auth.responses.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/login")
class LoginController(
    val loginService: LoginService
) {

    @PostMapping
    fun login(@RequestBody @Valid request: LoginRequest): TokenResponse {
        val token = loginService.verifyUserAndCreateToken(request.email, request.password)
        return TokenResponse(token)
    }
}
