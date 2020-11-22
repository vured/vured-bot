package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.music.MusicManager

object PauseReaction {
    const val emote = "U+23f8"

    fun execute() {
        MusicManager.audioPlayer.isPaused = true
    }
}
