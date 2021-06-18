package dev.jonaz.vured.bot.persistence.web

import io.ktor.auth.*

data class UserPrincipal(
    val discord: Long,
    val name: String
): Principal
