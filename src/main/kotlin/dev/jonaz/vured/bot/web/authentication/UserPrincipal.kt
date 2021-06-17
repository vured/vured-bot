package dev.jonaz.vured.bot.web.authentication

import io.ktor.auth.*

data class UserPrincipal(
    val discord: Long,
    val name: String
): Principal
