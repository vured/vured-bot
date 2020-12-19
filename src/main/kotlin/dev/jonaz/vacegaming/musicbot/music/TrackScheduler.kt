package dev.jonaz.vacegaming.musicbot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import dev.jonaz.vacegaming.musicbot.service.LogService
import dev.jonaz.vacegaming.musicbot.service.MusicService
import dev.jonaz.vacegaming.musicbot.service.StaticMessageService
import dev.jonaz.vacegaming.musicbot.service.VoiceChannelService
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import java.awt.Color

object TrackScheduler : AudioEventAdapter() {
    private val musicService by genericInject<MusicService>()
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val staticMessageService by genericInject<StaticMessageService>()
    private val logService by genericInject<LogService>()

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        val queue = musicService.getQueue()

        if (queue.size <= 0 && endReason.name != "REPLACED") {
            voiceChannelService.leave()
            logService.sendLog(
                title = Translation.LOG_VOICECHANNEL_LEFT,
                description = Translation.LOG_PLAYLIST_ENDED,
                member = null,
                color = Color.ORANGE
            )
            return
        }

        if (endReason.mayStartNext) {
            musicService.nextTrack()
        }
    }

    override fun onPlayerPause(audioPlayer: AudioPlayer) {
        staticMessageService.build(
            title = audioPlayer.playingTrack.info.title,
            description = audioPlayer.playingTrack.info.author,
            Color.ORANGE,
            audioPlayer.volume
        ).also { staticMessageService.set(it) }
    }

    override fun onPlayerResume(audioPlayer: AudioPlayer) {
        staticMessageService.build(
            title = audioPlayer.playingTrack.info.title,
            description = audioPlayer.playingTrack.info.author,
            Color.GREEN,
            audioPlayer.volume
        ).also { staticMessageService.set(it) }
    }

    override fun onTrackStart(audioPlayer: AudioPlayer, track: AudioTrack) {
        staticMessageService.build(
            title = audioPlayer.playingTrack.info.title,
            description = audioPlayer.playingTrack.info.author,
            Color.GREEN,
            audioPlayer.volume
        ).also { staticMessageService.set(it) }
    }

    override fun onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException?) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_VOICECHANNEL_LEFT,
            description = Translation.LOG_TRACK_EXCEPTION,
            member = null,
            color = Color.RED
        )
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {
        voiceChannelService.leave()
        logService.sendLog(
            title = Translation.LOG_VOICECHANNEL_LEFT,
            description = Translation.LOG_TRACK_STUCK,
            member = null,
            color = Color.RED
        )
    }
}
