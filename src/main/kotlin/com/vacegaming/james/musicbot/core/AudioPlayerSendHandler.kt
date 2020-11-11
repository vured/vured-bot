package com.vacegaming.james.musicbot.core

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.ByteBuffer

class AudioPlayerSendHandler(private val audioPlayer: AudioPlayer) : AudioSendHandler {
    private lateinit var lastFrame: AudioFrame

    override fun canProvide(): Boolean {
        lastFrame = audioPlayer.provide()
        return true
    }

    override fun provide20MsAudio(): ByteBuffer? {
        return ByteBuffer.wrap(lastFrame.data)
    }

    override fun isOpus(): Boolean {
        return true
    }
}
