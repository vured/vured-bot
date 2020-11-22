package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue

object StopReaction {
    const val emote = "U+23f9"

    fun execute() {
        MusicQueue.queue.clear()
        MusicManager.audioPlayer.stopTrack()
        VoiceChannelManager.leave()
    }
}
