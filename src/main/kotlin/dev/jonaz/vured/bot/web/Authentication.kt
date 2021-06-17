package dev.jonaz.vured.bot.web

import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.web.authentication.UserPrincipal
import dev.jonaz.vured.util.extensions.genericInject
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun configureAuthentication(configure: Authentication.Configuration) {
    val jwtService by genericInject<JwtService>()

    configure.jwt {
        realm = "vured"
        verifier(jwtService.getVerifier())
        validate {
            UserPrincipal(
                it.payload.getClaim("discord").asLong(),
                it.payload.getClaim("name").asString()
            )
        }
    }
}
