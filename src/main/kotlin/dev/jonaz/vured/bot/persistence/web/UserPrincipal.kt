package dev.jonaz.vured.bot.persistence.web

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class UserPrincipal(
    val discord: Long,
    val name: String,
    val avatar: String
): Principal
