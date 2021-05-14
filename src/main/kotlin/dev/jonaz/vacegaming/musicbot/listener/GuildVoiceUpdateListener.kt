package dev.jonaz.vacegaming.musicbot.listener

import dev.jonaz.vacegaming.musicbot.service.discord.GuildService
import dev.jonaz.vacegaming.musicbot.service.application.LogService
import dev.jonaz.vacegaming.musicbot.service.music.MusicService
import dev.jonaz.vacegaming.musicbot.service.discord.VoiceChannelService
import dev.jonaz.vacegaming.musicbot.application.Translation
import dev.jonaz.vured.util.extensions.genericInject
import dev.jonaz.vured.util.extensions.ifNotTrue
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class GuildVoiceUpdateListener : ListenerAdapter() {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val guildService by genericInject<GuildService>()
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        val guild = guildService.getCurrentGuild()
        val voiceState = guild?.selfMember?.voiceState
        val audioPlayer = musicService.getAudioPlayer()

        audioPlayer.playingTrack.let {
            if (it == null) return
        }

        if (voiceState?.channel != event.channelJoined && voiceState?.channel != event.channelLeft) {
            return
        }

        voiceState?.channel?.members?.size?.let {
            if (it <= 1) {
                voiceChannelService.leave()
                logService.sendLog(
                    title = Translation.LOG_VOICECHANNEL_LEFT,
                    description = Translation.LOG_VOICECHANNEL_EMPTY_DESCRIPTION,
                    member = null,
                    color = Color.RED
                )
            }
        }

        voiceState?.inVoiceChannel()?.ifNotTrue {
            return voiceChannelService.leave()
        }
    }
}
