package dev.jonaz.vacegaming.musicbot.model

data class BotConfigModel(
    val botLogChannelID: Long,
    val memberID: Long,
    val musicBotChannelID: Long,
    val guildId: Long,
    val maxPlaylistTracks: Int
)
