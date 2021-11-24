package com.vkashel.tasktracker.domain.services

import com.vkashel.tasktracker.domain.exceptions.InvalidCredentialsException
import com.vkashel.tasktracker.utilservices.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    val userService: UserService,
    val tokenProvider: JwtTokenProvider,
    val passwordEncoder: PasswordEncoder
) {
    fun verifyUserAndCreateToken(email: String, password: String): String {
        val user = userService.findUserByEmail(email)
        if (user == null || !passwordEncoder.matches(password, user.password)) {
            throw InvalidCredentialsException("Wrong credentials")
        }
        return tokenProvider.createJwtToken(user)
    }
}
