package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.GuildManager
import com.vacegaming.james.musicbot.core.MemberManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.util.ConfigManager
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

        MusicManager.play(member, event.message.contentDisplay)
    }
}
