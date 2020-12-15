package com.vacegaming.musicbot.core

import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.DiscordClient
import net.dv8tion.jda.api.entities.Guild

object GuildManager {
    private val guildId: Long = ConfigManager.data.vaceGamingGuildId

    val current: Guild? = DiscordClient.jda.getGuildById(guildId)
}
