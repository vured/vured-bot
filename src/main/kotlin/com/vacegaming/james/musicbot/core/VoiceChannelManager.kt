package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue
import net.dv8tion.jda.api.entities.VoiceChannel
import java.awt.Color

object VoiceChannelManager {
    var connectedChannel: VoiceChannel? = null

    fun join(channel: VoiceChannel?) {
        connect(channel)
    }

    fun leave() {
        val audioManager = GuildManager.current?.audioManager

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null
        connectedChannel = null

        ChannelManager.editStaticMessage("Sende einen Song rein um ihn abzuspielen", Color.RED, null)
    }

    private fun connect(channel: VoiceChannel?) {
        val audioManager = GuildManager.current?.audioManager ?: return

        if (audioManager.isConnected) {
            return
        }

        connectedChannel = channel
        GuildManager.current.audioManager.openAudioConnection(channel)
        MusicManager.audioPlayer.volume = 10
        /*GuildManager.current.selfMember.deafen(true)*/
    }
}
