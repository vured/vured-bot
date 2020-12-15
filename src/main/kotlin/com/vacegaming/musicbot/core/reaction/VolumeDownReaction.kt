package com.vacegaming.musicbot.core.reaction

import com.vacegaming.musicbot.core.ChannelManager
import com.vacegaming.musicbot.core.music.MusicManager
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
