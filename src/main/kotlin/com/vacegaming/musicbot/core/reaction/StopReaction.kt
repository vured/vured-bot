package com.vacegaming.musicbot.core.reaction

import com.vacegaming.musicbot.core.VoiceChannelManager
import net.dv8tion.jda.api.entities.Member

object StopReaction {
    const val emote = "U+23f9"

    fun execute(member: Member) {
        VoiceChannelManager.leave(title = "Stop", member = member)
    }
}
