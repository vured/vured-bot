package com.vacegaming.musicbot.service

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.musicbot.music.AudioPlayerSendHandler
import com.vacegaming.musicbot.music.AudioLoadResultManager
import com.vacegaming.musicbot.music.TrackScheduler
import net.dv8tion.jda.api.entities.Member
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class MusicService {
    private lateinit var playerManager: DefaultAudioPlayerManager
    private lateinit var audioPlayer: AudioPlayer
    private lateinit var sendHandler: AudioPlayerSendHandler

    private val queue: BlockingQueue<AudioTrack> = LinkedBlockingQueue()

    fun createAudioPlayer() {
        playerManager = DefaultAudioPlayerManager()
        audioPlayer = playerManager.createPlayer()
        sendHandler = AudioPlayerSendHandler(audioPlayer)

        AudioSourceManagers.registerRemoteSources(playerManager)
        audioPlayer.addListener(TrackScheduler)
    }

    fun loadItem(member: Member, url: String) {
        val audioLoadResult = AudioLoadResultManager(
            member = member
        )

        playerManager.loadItemOrdered(member.guild, url, audioLoadResult)
    }

    fun setVolume(value: Int) {
        audioPlayer.volume = value
    }

    fun getVolume(): Int {
        return audioPlayer.volume
    }

    fun stopTrack() {
        audioPlayer.stopTrack()
    }


    fun offerToQueue(track: AudioTrack) {
        queue.offer(track)
    }

    fun pollQueue(): AudioTrack? {
        return queue.poll()
    }

    fun startTrack(track: AudioTrack, noInterrupt: Boolean): Boolean {
        return audioPlayer.startTrack(track, noInterrupt)
    }

    fun setPause() = true.also { audioPlayer.isPaused = it }

    fun setResume() = false.also { audioPlayer.isPaused = it }

    fun getQueue() = queue

    fun getSendHandler() = sendHandler

    fun getAudioPlayer() = audioPlayer
}
