package dev.jonaz.vured.bot.web.configuration

import io.ktor.features.*
import io.ktor.http.*

fun configureCors(configure: CORS.Configuration) {
    configure.header("Authorization")

    configure.method(HttpMethod.Patch)
    configure.method(HttpMethod.Get)

    configure.allowNonSimpleContentTypes = true

    configure.host(
        host = "localhost:4200",
        schemes = listOf("http")
    )

    configure.host(
        host = "jonaz.dev",
        subDomains = listOf("vured-ui"),
        schemes = listOf("https")
    )

    configure.anyHost()
}
