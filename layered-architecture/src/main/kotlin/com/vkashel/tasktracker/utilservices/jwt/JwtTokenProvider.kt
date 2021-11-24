package com.vkashel.tasktracker.utilservices.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.vkashel.tasktracker.domain.entities.user.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.RuntimeException
import java.time.ZonedDateTime
import java.util.Date

@Component
class JwtTokenProvider(private val jwtProperties: JwtProperties) {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun verifyTokenAndGetUserId(token: String): Long {
        try {
            val algorithm = Algorithm.HMAC256(jwtProperties.secret)
            val jwtVerifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build()
            val decodedJwt = jwtVerifier.verify(token)
            val expiresAt = decodedJwt.expiresAt
            if (Date() > expiresAt) {
                throw JwtTokenException("token is expired")
            }
            return decodedJwt.claims["id"]?.asLong() ?: throw JwtTokenException("Can not find id claim in jwt token")
        } catch (ex: Exception) {
            logger.error("Error while verifying jwt token.", ex)
            throw RuntimeException("Authentication error", ex)
        }
    }

    fun createJwtToken(user: User): String {
        val algorithm = Algorithm.HMAC256(jwtProperties.secret)
        val expiredDate = Date.from(ZonedDateTime.now().plusSeconds(jwtProperties.expiredTimeInSeconds).toInstant())
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(expiredDate)
            .sign(algorithm)
    }
}
