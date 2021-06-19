package dev.jonaz.vured.bot.web.configuration

import dev.jonaz.vured.bot.persistence.web.UserPrincipal
import dev.jonaz.vured.bot.web.route.index
import dev.jonaz.vured.bot.web.route.user
import dev.jonaz.vured.bot.web.route.verification
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() = routing {
    index()
    verification()

    authenticate {
        user()
    }
}
