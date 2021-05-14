package dev.jonaz.vacegaming.musicbot.service.discord

import dev.jonaz.vacegaming.musicbot.service.application.ConfigService
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member

class GuildService {
    private val discordClientService by genericInject<DiscordClientService>()
    private val config by ConfigService

    fun getCurrentGuild(): Guild? {
        return discordClientService.JDA.getGuildById(config.discord.guild)
    }

    fun getSelfMember(): Member? {
        return getCurrentGuild()?.selfMember
    }
}
