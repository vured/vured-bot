package dev.jonaz.vured.bot.persistence.web

import kotlinx.serialization.Serializable

@Serializable
data class PlayerEventQueueItem(
    val title: String?,
    val uri: String?,
    val identifier: String?
)
