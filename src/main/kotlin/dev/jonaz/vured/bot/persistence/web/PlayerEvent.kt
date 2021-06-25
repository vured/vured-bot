package dev.jonaz.vured.bot.persistence.web

import kotlinx.serialization.Serializable

@Serializable
data class PlayerEvent(
    val isPaused: Boolean,
    val volume: Int,
    val title: String?,
    val author: String?,
    val isStream: Boolean?,
    val uri: String?,
    val duration: Long?,
    val position: Long?,
    val identifier: String?,
    var queue: List<PlayerEventQueueItem>? = null
)
