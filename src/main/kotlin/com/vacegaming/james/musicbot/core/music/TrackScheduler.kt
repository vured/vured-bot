package com.vacegaming.james.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason

object TrackScheduler : AudioEventAdapter() {

    fun queue(track: AudioTrack) {
        val wasStarted = MusicManager.audioPlayer.startTrack(track, true)

        if (wasStarted.not()) {
            MusicQueue.queue.offer(track)
        }
    }

    fun nextTrack() = MusicManager.audioPlayer.startTrack(
        MusicQueue.queue.poll(),
        false
    )

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        println("onTrackEnd")
        if (endReason.mayStartNext) nextTrack()
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        println("onTrackStart")
    }
}
