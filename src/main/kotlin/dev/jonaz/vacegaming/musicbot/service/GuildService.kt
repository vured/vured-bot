package dev.jonaz.vacegaming.musicbot.service

import dev.jonaz.vacegaming.musicbot.util.discord.DiscordClient
import dev.jonaz.vacegaming.musicbot.util.data.Config
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member

class GuildService {
    fun getCurrentGuild(): Guild? {
        return DiscordClient.JDA.getGuildById(Config.data.vaceGamingGuildId)
    }

    fun getSelfMember(): Member? {
        return getCurrentGuild()?.selfMember
    }
}
