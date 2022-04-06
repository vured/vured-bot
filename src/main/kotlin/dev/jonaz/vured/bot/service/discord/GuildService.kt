package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member

class GuildService {
    private val discordClientService by genericInject<DiscordClientService>()
    private val config by ConfigService

    fun getCurrentGuild(): Guild? {
        return discordClientService.jda.getGuildById(config.discord.guild)
    }

    fun getSelfMember(): Member? {
        return getCurrentGuild()?.selfMember
    }
}
