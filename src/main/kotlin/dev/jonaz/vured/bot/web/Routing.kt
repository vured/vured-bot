package dev.jonaz.vured.bot.web

import dev.jonaz.vured.bot.web.authentication.UserPrincipal
import dev.jonaz.vured.bot.web.route.index
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        index()

        authenticate {
            get("/welcome") {
                val principal = call.authentication.principal<UserPrincipal>()
                val name = principal?.name
                call.respondText("Hello, $name!")
            }
        }
    }
}
