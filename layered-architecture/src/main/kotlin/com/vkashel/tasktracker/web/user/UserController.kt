package com.vkashel.tasktracker.web.user

import com.vkashel.tasktracker.domain.entities.User
import com.vkashel.tasktracker.domain.services.UserService
import com.vkashel.tasktracker.web.user.responses.UserInfoResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: User): UserInfoResponse {
        return UserInfoResponse.of(user)
    }
}
