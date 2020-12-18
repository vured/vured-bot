package com.vacegaming.musicbot.listener

import com.vacegaming.musicbot.core.GuildManager
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.VoiceChannelService
import com.vacegaming.musicbot.util.ifNotTrue
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildVoiceUpdateListener : ListenerAdapter() {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val musicService by genericInject<MusicService>()

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        val voiceState = GuildManager.current?.selfMember?.voiceState
        val audioPlayer = musicService.getAudioPlayer()

        audioPlayer.playingTrack.let {
            if (it == null) return
        }

        if (voiceState?.channel != event.channelJoined && voiceState?.channel != event.channelLeft) {
            return
        }

        voiceState?.channel?.members?.size?.let {
            if (it <= 1) voiceChannelService.leave("Sprach-Kanal war leer")
        }

        voiceState?.inVoiceChannel()?.ifNotTrue {
            return voiceChannelService.leave()
        }
    }
}
