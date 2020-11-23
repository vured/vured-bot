package com.vacegaming.james.musicbot.core.reaction

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue
import net.dv8tion.jda.api.entities.Member

object StopReaction {
    const val emote = "U+23f9"

    fun execute(member: Member) {
        ChannelManager.sendLog("Stop", null, member)
        MusicQueue.queue.clear()
        MusicManager.audioPlayer.stopTrack()
        VoiceChannelManager.leave()
    }
}
