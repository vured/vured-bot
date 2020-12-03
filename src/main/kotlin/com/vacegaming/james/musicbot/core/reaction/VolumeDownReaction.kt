package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import java.awt.Color

object VolumeDownReaction {
    const val emote = "U+2796"

    fun execute() = MusicManager.audioPlayer.run {
        this.volume = this.volume - 5
    }.also {
        val audioPlayer = MusicManager.audioPlayer
        val title = audioPlayer.playingTrack.info.title

        ChannelManager.editStaticMessage(title, null, Color.green, audioPlayer.volume)
    }
}