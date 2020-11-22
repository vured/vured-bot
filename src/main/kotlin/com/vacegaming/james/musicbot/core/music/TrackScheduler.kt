package com.vacegaming.james.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.vacegaming.james.musicbot.core.VoiceChannelManager

object TrackScheduler : AudioEventAdapter() {

    fun queue(track: AudioTrack) {
        val wasStarted = MusicManager.audioPlayer.startTrack(track, true)

        if (wasStarted.not()) {
            MusicQueue.queue.offer(track)
        }
    }

    fun nextTrack() {
        val queue = MusicQueue.queue

        if (queue.size > 0) {
            MusicManager.audioPlayer.startTrack(queue.poll(), false)
        }
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        if (endReason.mayStartNext) {
            nextTrack()
        } else {
            VoiceChannelManager.leave()
        }
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        println("onTrackStart")
    }
}
