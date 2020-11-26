package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import net.dv8tion.jda.api.entities.Member

object PlayReaction {
    const val emote = "U+25b6"

    fun execute(member: Member) {
        ChannelManager.sendLog(title = "Play", member = member)
        MusicManager.audioPlayer.isPaused = false
    }
}
