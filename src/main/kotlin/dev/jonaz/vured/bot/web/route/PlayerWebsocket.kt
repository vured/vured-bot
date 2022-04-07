package dev.jonaz.vured.bot.web.route

import dev.jonaz.vured.bot.service.web.JwtService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.util.extensions.genericInject
import io.ktor.routing.*
import io.ktor.websocket.*

fun Route.playerWebsocket() {
    val jwtService by genericInject<JwtService>()
    val playerService by genericInject<PlayerService>()

    webSocket("/player/{token}") {
        val token = call.parameters["token"]

        jwtService.getVerifier()
            .runCatching {
                this.verify(token)
            }
            .onSuccess {
                playerService.getEvent().let {
                    playerService.convertToFrame(it).run { outgoing.send(this) }
                }

                playerService.events.collect {
                    playerService.convertToFrame(it).run { outgoing.send(this) }
                }
            }
    }

    webSocket("/player/message/{token}") {
        val token = call.parameters["token"]

        jwtService.getVerifier()
            .runCatching {
                this.verify(token)
            }
            .onSuccess {
                playerService.messageEvents.collect {
                    playerService.convertToFrame(it).run { outgoing.send(this) }
                }
            }
    }
}
