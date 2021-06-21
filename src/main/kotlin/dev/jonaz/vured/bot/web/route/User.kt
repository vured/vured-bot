package dev.jonaz.vured.bot.web.route

import dev.jonaz.vured.bot.persistence.web.UserPrincipal
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.user() {
    get("/user") {
        val principal = call.authentication.principal<UserPrincipal>()

        if(principal == null) {
            call.respond(HttpStatusCode.NotFound)
        } else {
            call.respond(principal)
        }
    }
}
