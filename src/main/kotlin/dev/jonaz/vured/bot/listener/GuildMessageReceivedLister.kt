package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.service.application.ConfigService
import dev.jonaz.vured.bot.service.discord.MemberService
import dev.jonaz.vured.bot.service.discord.GuildService
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.util.extensions.genericInject
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.util.concurrent.TimeUnit

class GuildMessageReceivedLister : ListenerAdapter() {
    private val musicService by genericInject<MusicService>()
    private val guildService by genericInject<GuildService>()
    private val logService by genericInject<LogService>()
    private val memberService by genericInject<MemberService>()

    private val config by ConfigService

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val guild = guildService.getCurrentGuild()
        val audioManager = guild?.audioManager ?: return

        if (event.message.author.isBot) {
            return
        }

        if (event.channel.idLong != config.discord.musicChannel) {
            return
        }

        if (memberService.isPermitted(event.member).not()) {
            event.message.delete().queue()
            return
        }

        if (audioManager.isConnected && memberService.isInChannel(event.member).not()) {
            return event.message.delete().queue()
        }

        event.message.delete().delay(1500, TimeUnit.MILLISECONDS).queue()

        musicService.loadItem(event.member, event.message.contentDisplay)
        musicService.setResume()

        logService.sendLog(
            title = Translation.LOG_TRACK_LOADED_TITLE,
            description = Translation.LOG_TRACK_LOADED_DESCRIPTION,
            member = event.member,
            color = Color.GREEN
        )
    }
}
