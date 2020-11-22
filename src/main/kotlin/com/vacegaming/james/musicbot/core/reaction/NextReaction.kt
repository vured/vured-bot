package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.music.TrackScheduler

object NextReaction {
    const val emote = "U+23ed"

    fun execute() {
        TrackScheduler.nextTrack()
    }
}
