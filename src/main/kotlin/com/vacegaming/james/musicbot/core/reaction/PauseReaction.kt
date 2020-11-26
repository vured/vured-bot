package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import net.dv8tion.jda.api.entities.Member

object PauseReaction {
    const val emote = "U+23f8"

    fun execute(member: Member) {
        ChannelManager.sendLog(title = "Pause", member = member)
        MusicManager.audioPlayer.isPaused = true
    }
}
