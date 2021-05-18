package dev.jonaz.vured.bot.web

import dev.jonaz.vured.bot.web.route.index
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        index()
    }
}
