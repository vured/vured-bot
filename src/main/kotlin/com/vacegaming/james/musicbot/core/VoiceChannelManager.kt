package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.core.music.MusicManager
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel

object VoiceChannelManager {
    var connectedChannel: VoiceChannel? = null

    fun join(channel: VoiceChannel?) {
        connect(channel)
    }

    fun join(member: Member?, channel: VoiceChannel?) {
        val memberIsInChannel = member?.voiceState?.inVoiceChannel() ?: return

        if (memberIsInChannel.not()) {
            return
        }

        connect(channel)
    }

    fun leave() {
        val audioManager = GuildManager.current?.audioManager

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null
        connectedChannel = null
    }

    private fun connect(channel: VoiceChannel?) {
        val audioManager = GuildManager.current?.audioManager ?: return

        if (audioManager.isConnected) {
            return
        }

        connectedChannel = channel
        GuildManager.current.audioManager.openAudioConnection(channel)
        MusicManager.audioPlayer.volume = 10
    }
}
