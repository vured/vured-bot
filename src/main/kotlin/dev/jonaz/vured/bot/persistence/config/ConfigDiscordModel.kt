package dev.jonaz.vured.bot.persistence.config

data class ConfigDiscordModel(
    val guild: Long,
    val logChannel: Long,
    val musicChannel: Long,
    val accessRole: Long
)
