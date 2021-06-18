package dev.jonaz.vured.bot.service.web

import dev.jonaz.vured.bot.web.configureAuthentication
import dev.jonaz.vured.bot.web.configureRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class WebService {
    fun startServer() = embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0"
    ) {
        install(Authentication, ::configureAuthentication)
        install(ContentNegotiation) {
            json()
        }

        configureRouting()
    }.start(wait = true)
}
