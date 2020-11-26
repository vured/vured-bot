package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.GuildManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildVoiceUpdateListener : ListenerAdapter() {

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        val voiceState = GuildManager.current?.selfMember?.voiceState

        MusicManager.audioPlayer.playingTrack.let {
            if (it == null) return
        }

        if (voiceState?.channel != event.channelJoined && voiceState?.channel != event.channelLeft) {
            return
        }

        voiceState?.channel?.members?.size?.let {
            if(it <= 1) VoiceChannelManager.leave(title = "Stop", text = "Voicechannel war leer ($it)")
        }

        if (voiceState?.inVoiceChannel() != true) {
            return VoiceChannelManager.leave()
        }
    }
}
