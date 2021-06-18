package dev.jonaz.vured.bot.persistence.web

import kotlinx.serialization.Serializable

@Serializable
data class IndexDTO(
    val message: String,
    val version: String
)
