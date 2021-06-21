package dev.jonaz.vured.bot.web.configuration

import dev.jonaz.vured.bot.web.route.index
import dev.jonaz.vured.bot.web.route.player
import dev.jonaz.vured.bot.web.route.user
import dev.jonaz.vured.bot.web.route.verification
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

fun Application.configureRouting() = routing {
    index()
    verification()

    player()

    authenticate {
        user()
    }
}
