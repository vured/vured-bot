package dev.jonaz.vured.bot.service.web

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.jonaz.vured.bot.persistence.web.UserPrincipal
import java.util.*

class JwtService {
    private val validity = 36_000_00 * 48
    private lateinit var secret: String

    init {
        generateSecret()
    }

    fun createToken(user: UserPrincipal): String = JWT
        .create()
        .withSubject("Authentication")
        .withIssuer("vured")
        .withClaim("discord", user.discord)
        .withClaim("name", user.name)
        .withClaim("avatar", user.avatar)
        .withExpiresAt(
            Date(System.currentTimeMillis() + validity)
        )
        .sign(
            Algorithm.HMAC512(secret)
        )

    fun getVerifier(): JWTVerifier = JWT
        .require(
            Algorithm.HMAC512(secret)
        )
        .withIssuer("vured")
        .build()

    private fun generateSecret() {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val outputStrLength = (64..128).shuffled().first()

        secret = (1..outputStrLength)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}
