package dev.jonaz.vacegaming.musicbot.listener

import dev.jonaz.vacegaming.musicbot.service.MemberService
import dev.jonaz.vacegaming.musicbot.service.GuildService
import dev.jonaz.vacegaming.musicbot.service.LogService
import dev.jonaz.vacegaming.musicbot.service.MusicService
import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class GuildMessageReceivedLister : ListenerAdapter() {
    private val musicService by genericInject<MusicService>()
    private val guildService by genericInject<GuildService>()
    private val logService by genericInject<LogService>()
    private val memberService by genericInject<MemberService>()

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val guild = guildService.getCurrentGuild()
        val audioManager = guild?.audioManager ?: return

        if (event.message.author.isBot) {
            return
        }

        if (event.channel.idLong != Config.musicChannel) {
            return
        }

        if (memberService.isPermitted(event.member).not()) {
            event.message.delete().queue()
            return
        }

        if (audioManager.isConnected && memberService.isInChannel(event.member).not()) {
            return event.message.delete().queue()
        }

        event.message.delete().queue()

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
