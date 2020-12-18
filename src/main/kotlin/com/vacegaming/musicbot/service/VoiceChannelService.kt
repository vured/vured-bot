package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.core.GuildManager
import com.vacegaming.musicbot.util.ifNotTrue
import com.vacegaming.musicbot.util.ifTrue
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.AudioManager
import java.awt.Color

class VoiceChannelService {
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val musicService by genericInject<MusicService>()

    fun join(member: Member): AudioManager? {
        val selfVoiceState = GuildManager.current?.selfMember?.voiceState

        selfVoiceState?.inVoiceChannel().ifTrue {
            return GuildManager.current?.audioManager
        }

        member.voiceState?.inVoiceChannel()?.ifNotTrue {
            musicChannelService.sendMessage(
                color = Color.BLUE,
                text = "Bitte gehe zuerst in einen Sprach-Kanal",
                timeout = 5000
            )
            return null
        }

        return member.voiceState?.channel.run(::join)
    }

    fun leave(reason: String? = null, member: Member? = null) {
        val audioManager = GuildManager.current?.audioManager

        musicService.stopTrack()
        playlistService.deleteQuestionMessage()

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null
    }

    private fun join(channel: VoiceChannel?): AudioManager? {
        val audioManager = GuildManager.current?.audioManager

        audioManager?.openAudioConnection(channel)
        musicService.setVolume(10)

        return audioManager
    }
}
