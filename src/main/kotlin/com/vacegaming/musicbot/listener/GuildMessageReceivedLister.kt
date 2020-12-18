package com.vacegaming.musicbot.listener

import com.vacegaming.musicbot.core.GuildManager
import com.vacegaming.musicbot.core.MemberManager
import com.vacegaming.musicbot.service.LogService
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.util.ConfigManager
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildMessageReceivedLister : ListenerAdapter() {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val member = event.member ?: return
        val isBot = event.message.author.isBot
        val channelId = event.channel.idLong
        val musicBotChannelId = ConfigManager.data.musicBotChannelID
        val audioManager = GuildManager.current?.audioManager ?: return

        when {
            isBot -> return
            channelId != musicBotChannelId -> return
            MemberManager.isPermitted(member).not() -> return event.message.delete().queue()
        }

        if (audioManager.isConnected && MemberManager.isInChannel(member).not()) {
            return event.message.delete().queue()
        }

        event.message.delete().queue()

        logService.sendLog("Song hinzugefügt", event.message.contentDisplay, member)
        musicService.loadItem(member, event.message.contentDisplay)
        musicService.setResume()
    }
}
