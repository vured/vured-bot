package dev.jonaz.vured.bot.web.route

import dev.jonaz.vured.bot.persistence.web.IndexDTO
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.util.extensions.genericInject
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.index() {
    val logService by genericInject<LogService>()

    get("/") {
        call.respond(
            IndexDTO("vured api server listening here", logService.getImplementationVersion() ?: "")
        )
    }
}
