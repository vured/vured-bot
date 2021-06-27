package dev.jonaz.vured.bot.persistence.web

import kotlinx.serialization.Serializable

@Serializable
data class PlayerMessageEvent(
    val exception: Boolean,
    val message: String
)
