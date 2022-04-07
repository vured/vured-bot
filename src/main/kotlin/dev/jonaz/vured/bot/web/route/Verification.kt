package dev.jonaz.vured.bot.web.route

import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.util.extensions.genericInject
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable

fun Route.verification() {
    val logService by genericInject<LogService>()

    get("/verification") {
        call.respond(
            VerificationDTO("vured", logService.getImplementationVersion() ?: "")
        )
    }

    authenticate {
        get("/verification/authenticated") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

@Serializable
data class VerificationDTO(
    val serverName: String,
    val version: String
)
