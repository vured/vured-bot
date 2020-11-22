package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.util.ConfigManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildMessageReceivedLister : ListenerAdapter() {

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.message.channel.idLong != ConfigManager.data.musicBotChannelID) return
        if (event.message.author.isBot) return

        event.message.delete().queue()

        val audioManager = event.guild.audioManager
        val member = event.member ?: return

        if (audioManager.isConnected.not()) {
            val memberVoiceState = event.member?.voiceState

            if (memberVoiceState?.inVoiceChannel() == true) {
                audioManager.openAudioConnection(memberVoiceState.channel)
                MusicManager.audioPlayer.volume = 10
            }
        }

        MusicManager.play(member, event.message.contentDisplay)
    }
}
