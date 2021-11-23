package com.vkashel.tasktracker.utilservices.jwt

import com.vkashel.tasktracker.repository.api.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "")
        val userId = jwtTokenProvider.verifyTokenAndGetUserId(tokenHeader)
        val user = userRepository.find(userId) ?: throw RuntimeException("user was not found")
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(user.id, user.password, listOf(SimpleGrantedAuthority(user.role)))
        filterChain.doFilter(request, response)
    }
}