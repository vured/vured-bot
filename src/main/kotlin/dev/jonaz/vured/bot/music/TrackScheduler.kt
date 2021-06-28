package dev.jonaz.vured.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import dev.jonaz.vured.bot.application.Translation
import dev.jonaz.vured.bot.service.application.LogService
import dev.jonaz.vured.bot.service.discord.StaticMessageService
import dev.jonaz.vured.bot.service.discord.VoiceChannelService
import dev.jonaz.vured.bot.service.music.MusicService
import dev.jonaz.vured.bot.service.web.PlayerService
import dev.jonaz.vured.util.extensions.genericInject
import java.awt.Color

object TrackScheduler : AudioEventAdapter() {
    private val musicService by genericInject<MusicService>()
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val staticMessageService by genericInject<StaticMessageService>()
    private val logService by genericInject<LogService>()
    private val playerService by genericInject<PlayerService>()

    override fun onTrackEnd(audioPlayer: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        val queue = musicService.getQueue()

        if (queue.size <= 0 && endReason.name != "REPLACED") {
            voiceChannelService.leave()
            logService.sendLog(
                title = Translation.LOG_VOICE_CHANNEL_LEFT,
                description = Translation.LOG_PLAYLIST_ENDED,
                member = null,
                color = Color.ORANGE
            )
            return
        }

        if (endReason.mayStartNext) {
            musicService.nextTrack()
        }

        playerService.sendEvent(audioPlayer)
    }

    override fun onPlayerPause(audioPlayer: AudioPlayer) {
        staticMessageService.build(
            title = audioPlayer.playingTrack?.info?.title,
            description = audioPlayer.playingTrack?.info?.author,
            color = Color.ORANGE,
            volume = audioPlayer.volume,
            audioTrack = audioPlayer.playingTrack
        ).also { staticMessageService.set(it) }

        playerService.sendEvent(audioPlayer)
    }

    override fun onPlayerResume(audioPlayer: AudioPlayer) {
        staticMessageService.build(
            title = audioPlayer.playingTrack?.info?.title,
            description = audioPlayer.playingTrack?.info?.author,
            color = Color.decode("#2F3136"),
            volume = audioPlayer.volume,
            audioTrack = audioPlayer.playingTrack
        ).also { staticMessageService.set(it) }

        playerService.sendEvent(audioPlayer)
    }

    override fun onTrackStart(audioPlayer: AudioPlayer, track: AudioTrack) {
        staticMessageService.build(
            title = audioPlayer.playingTrack?.info?.title,
            description = audioPlayer.playingTrack?.info?.author,
            color = Color.decode("#2F3136"),
            volume = audioPlayer.volume,
            audioTrack = track
        ).also { staticMessageService.set(it) }

        playerService.sendEvent(audioPlayer)
    }

    override fun onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException?) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_VOICE_CHANNEL_LEFT,
            description = Translation.LOG_TRACK_EXCEPTION,
            member = null,
            color = Color.RED
        )

        exception?.message?.let {
            playerService.sendMessageEvent(true, it)
        }
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_VOICE_CHANNEL_LEFT,
            description = Translation.LOG_TRACK_STUCK,
            member = null,
            color = Color.RED
        )
        playerService.sendMessageEvent(true, Translation.LOG_TRACK_STUCK)
    }
}
