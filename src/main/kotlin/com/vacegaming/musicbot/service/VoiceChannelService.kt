package com.vacegaming.musicbot.service

import com.vacegaming.musicbot.util.data.Translation
import com.vacegaming.musicbot.util.application.ifNotTrue
import com.vacegaming.musicbot.util.application.ifTrue
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.AudioManager
import java.awt.Color

class VoiceChannelService {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val guildService by genericInject<GuildService>()
    private val musicService by genericInject<MusicService>()

    fun join(member: Member): AudioManager? {
        val selfMember = guildService.getSelfMember()
        val selfVoiceState = selfMember?.voiceState

        selfVoiceState?.inVoiceChannel().ifTrue {
            return musicService.getGuildAudioManager()
        }

        member.voiceState?.inVoiceChannel()?.ifNotTrue {
            musicChannelService.sendMessage(
                color = Color.BLUE,
                text = Translation.NO_VOICECHANNEL,
                timeout = 5000
            )
            return null
        }

        return member.voiceState?.channel.run(::join)
    }

    fun leave() {
        val audioManager = musicService.getGuildAudioManager()

        musicService.stopTrack()
        playlistService.deleteQuestionMessage()

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null

        musicService.clearQueue()

        staticMessageService.build(
            title = Translation.NO_TRACK_TITLE,
            description = Translation.NO_TRACK_DESCRIPTION,
            color = Color.RED,
            volume = null
        ).also { staticMessageService.set(it) }
    }

    private fun join(channel: VoiceChannel?): AudioManager? {
        val audioManager = musicService.getGuildAudioManager()

        audioManager?.openAudioConnection(channel)
        musicService.setVolume(10)

        return audioManager
    }
}
