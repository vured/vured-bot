package dev.jonaz.vured.bot.service.application

import dev.jonaz.vured.bot.web.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class WebService {
    fun startServer() = embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0"
    ) {
        configureRouting()
    }.start(wait = true)
}
