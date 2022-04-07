package dev.jonaz.vured.bot.listener

import dev.jonaz.vured.bot.service.discord.GuildService
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.discord.VoiceChannelService
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.util.extensions.genericInject
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
                    title = Translation.LOG_VOICE_CHANNEL_LEFT,
                    description = Translation.LOG_VOICE_CHANNEL_EMPTY_DESCRIPTION,
                    member = null,
                    color = Color.RED
                )
            }
        }

        if (voiceState?.inAudioChannel() == false) {
            return voiceChannelService.leave()
        }
    }
}
