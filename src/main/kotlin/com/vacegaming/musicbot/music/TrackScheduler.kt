package com.vacegaming.musicbot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.service.StaticMessageService
import com.vacegaming.musicbot.service.VoiceChannelService
import com.vacegaming.musicbot.util.ifNotTrue
import com.vacegaming.musicbot.util.koin.genericInject
import java.awt.Color

object TrackScheduler : AudioEventAdapter() {
    private val musicService by genericInject<MusicService>()
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val staticMessageService by genericInject<StaticMessageService>()

    fun queue(track: AudioTrack) {
        musicService.startTrack(track, true).run {
            this.ifNotTrue { musicService.offerToQueue(track) }
        }

        val playingTrack = musicService.getAudioPlayer().playingTrack
        val volume = musicService.getVolume()

        staticMessageService.build(
            title = playingTrack.info.title,
            description = null,
            color = Color.GREEN,
            volume = volume,
            queue = null
        ).also { staticMessageService.set(it) }
    }

    fun nextTrack() {
        musicService.pollQueue()?.let {
            musicService.startTrack(it, false)
        }
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        val queue = musicService.getQueue()

        if (queue.size <= 0 && endReason.name != "REPLACED") {
            return voiceChannelService.leave()
        }

        if (endReason.mayStartNext) {
            nextTrack()
        }
    }

    override fun onPlayerPause(player: AudioPlayer) {
        val title = player.playingTrack.info.title

        staticMessageService.build(title, null, Color.ORANGE, player.volume, null)
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        val title = player?.playingTrack?.info?.title ?: ""

        staticMessageService.build(title, null, Color.GREEN, player?.volume, null)
    }

    override fun onTrackStart(player: AudioPlayer, track: AudioTrack) {
        val title = player.playingTrack.info.title

        staticMessageService.build(title, null, Color.GREEN, player.volume, null)
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        voiceChannelService.leave()
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        voiceChannelService.leave()
    }
}
