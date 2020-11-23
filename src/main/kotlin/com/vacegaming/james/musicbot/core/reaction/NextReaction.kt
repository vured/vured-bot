package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.music.TrackScheduler
import net.dv8tion.jda.api.entities.Member

object NextReaction {
    const val emote = "U+23ed"

    fun execute(member: Member) {
        ChannelManager.sendLog("Next", null, member)
        TrackScheduler.nextTrack()
    }
}
