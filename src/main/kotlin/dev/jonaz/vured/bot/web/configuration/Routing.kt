package dev.jonaz.vured.bot.web.configuration

import dev.jonaz.vured.bot.web.route.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

fun Application.configureRouting() = routing {
    index()
    verification()

    playerWebsocket()

    authenticate {
        user()
        player()
    }
}
