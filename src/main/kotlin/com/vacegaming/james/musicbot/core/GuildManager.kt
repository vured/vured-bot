package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.DiscordClient
import net.dv8tion.jda.api.entities.Guild

object GuildManager {
    private val guildId: Long = ConfigManager.data.vaceGamingGuildId

    val current: Guild? = DiscordClient.jda.getGuildById(guildId)
}
