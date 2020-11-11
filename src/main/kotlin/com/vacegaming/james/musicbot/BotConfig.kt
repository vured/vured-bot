package com.vacegaming.james.musicbot

data class BotConfig(
    val botLogChannelID: Long,
    val botToken: String,
    val clubMemberID: Long,
    val memberID: Long,
    val musicBotChannelID: Long,
    val musicBotID: Long,
    val vaceGamingGuildId: Long
)
