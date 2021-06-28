package dev.jonaz.vured.bot.web.route

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.index() {
    get {
        call.respondRedirect("https://github.com/vured/vured-bot/wiki", true)
    }
}
