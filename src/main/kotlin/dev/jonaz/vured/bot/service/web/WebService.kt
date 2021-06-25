package dev.jonaz.vured.bot.service.web

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.web.configuration.configureAuthentication
import dev.jonaz.vured.bot.web.configuration.configureCors
import dev.jonaz.vured.bot.web.configuration.configureRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*

class WebService {
    private val config by ConfigService

    fun startServer() = embeddedServer(
        factory = Netty,
        port = config.port,
        host = "0.0.0.0"
    ) {
        install(DefaultHeaders)
        install(WebSockets)
        install(CORS, ::configureCors)
        install(Authentication, ::configureAuthentication)

        install(ContentNegotiation) {
            json()
        }

        configureRouting()
    }.start(wait = true)
}
