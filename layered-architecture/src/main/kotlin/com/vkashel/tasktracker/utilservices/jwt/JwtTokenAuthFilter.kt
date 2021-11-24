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
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || header.isEmpty()) {
            return filterChain.doFilter(request, response)
        }
        val tokenHeader = header.replace("Bearer ", "")
        if (tokenHeader.isEmpty()) {
            return filterChain.doFilter(request, response)
        }
        val userId = jwtTokenProvider.verifyTokenAndGetUserId(tokenHeader)
        val user = userRepository.findById(userId) ?: throw RuntimeException("user was not found")
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(user, user.password, listOf(SimpleGrantedAuthority(user.role.name)))
        filterChain.doFilter(request, response)
    }
}
