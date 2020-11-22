package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.music.MusicManager

object PlayReaction {
    const val emote = "U+25b6"

    fun execute() {
        MusicManager.audioPlayer.isPaused = false
    }
}
