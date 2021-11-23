package com.vkashel.tasktracker.web.auth

import com.vkashel.tasktracker.domain.entities.User
import com.vkashel.tasktracker.domain.services.UserService
import com.vkashel.tasktracker.web.auth.requests.UserRegistrationRequest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth/registration")
class RegistrationController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registration(@RequestBody @Valid request: UserRegistrationRequest) {
        userService.registerUser(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = "USER",
                createdTime = ZonedDateTime.now()
            )
        )
    }
}