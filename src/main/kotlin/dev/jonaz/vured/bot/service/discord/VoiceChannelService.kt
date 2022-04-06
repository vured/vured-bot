package dev.jonaz.vured.bot.service.discord

import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.music.PlaylistService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.bot.util.extensions.genericInject
import net.dv8tion.jda.api.entities.AudioChannel
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.managers.AudioManager
import java.awt.Color

class VoiceChannelService {
    private val staticMessageService by genericInject<StaticMessageService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val guildService by genericInject<GuildService>()
    private val musicService by genericInject<MusicService>()
    private val playerService by genericInject<PlayerService>()

    fun join(member: Member): AudioManager? {
        val selfMember = guildService.getSelfMember()
        val selfVoiceState = selfMember?.voiceState

        if (selfVoiceState?.inAudioChannel() == true) {
            return musicService.getGuildAudioManager()
        }

        if (member.voiceState?.inAudioChannel() == false) {
            musicChannelService.sendMessage(
                color = Color.BLUE,
                text = Translation.NO_VOICE_CHANNEL,
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
            color = Color.decode("#2F3136"),
            volume = null
        ).also { staticMessageService.set(it) }

        musicService.getAudioPlayer().runCatching {
            playerService.sendEvent(this)
        }
    }

    private fun join(channel: AudioChannel?): AudioManager? {
        val audioManager = musicService.getGuildAudioManager()

        audioManager?.openAudioConnection(channel)
        musicService.setVolume(10)

        return audioManager
    }
}
