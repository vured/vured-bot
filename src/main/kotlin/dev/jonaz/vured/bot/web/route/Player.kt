package dev.jonaz.vured.bot.web.route

import com.auth0.jwt.exceptions.JWTVerificationException
import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.util.extensions.genericInject
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect

fun Route.player() {
    val jwtService by genericInject<JwtService>()
    val playerService by genericInject<PlayerService>()

    webSocket("/player/{token}") {
        val token = call.parameters["token"]
        val verifier = jwtService.getVerifier()

        try {
            verifier.verify(token)
        } catch (_: JWTVerificationException) {
            call.respond(HttpStatusCode.Unauthorized)
        }

        playerService.getEvent().let {
            playerService.convertEventToFrame(it).run { outgoing.send(this) }
        }

        playerService.events.collect {
            playerService.convertEventToFrame(it).run { outgoing.send(this) }
        }
    }
}
