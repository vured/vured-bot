package dev.jonaz.vured.bot.web.configuration

import io.ktor.features.*

fun configureCors(configure: CORS.Configuration) {
    configure.header("Authorization")

    configure.host(
        host = "localhost:4200",
        schemes = listOf("http")
    )

    configure.host(
        host = "jonaz.dev",
        subDomains = listOf("vured-ui"),
        schemes = listOf("https")
    )
}
