package com.vacegaming.musicbot.listener

import com.vacegaming.musicbot.core.ChannelManager
import com.vacegaming.musicbot.core.GuildManager
import com.vacegaming.musicbot.core.MemberManager
import com.vacegaming.musicbot.core.music.MusicManager
import com.vacegaming.musicbot.util.ConfigManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildMessageReceivedLister : ListenerAdapter() {

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


        ChannelManager.sendLog("Song hinzugefügt", event.message.contentDisplay, member)
        MusicManager.play(member, event.message.contentDisplay)
        MusicManager.audioPlayer.isPaused = false
    }
}
