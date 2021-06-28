package dev.jonaz.vured.bot.web.configuration

import com.fasterxml.jackson.databind.node.TextNode
import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.persistence.web.UserPrincipal
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
                it.payload.getClaim("discord").asString(),
                it.payload.getClaim("name").asString(),
                it.payload.getClaim("avatar").`as`(TextNode::class.java).asText()
            )
        }
    }
}
