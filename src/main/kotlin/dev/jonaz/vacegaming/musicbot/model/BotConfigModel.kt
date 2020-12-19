package dev.jonaz.vacegaming.musicbot.model

data class BotConfigModel(
    val botLogChannelID: Long,
    val memberID: Long,
    val musicBotChannelID: Long,
    val musicBotID: Long,
    val vaceGamingGuildId: Long,
    val maxPlaylistTracks: Int
)
