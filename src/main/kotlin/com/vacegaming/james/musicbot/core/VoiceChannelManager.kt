package com.vacegaming.james.musicbot.core

import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue
import com.vacegaming.james.musicbot.core.music.PlaylistManager
import com.vacegaming.james.musicbot.util.ConfigManager
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import java.awt.Color

object VoiceChannelManager {
    fun leave(title: String? = null, text: String? = null, member: Member? = null) {
        val audioManager = GuildManager.current?.audioManager

        MusicQueue.queue.clear()
        MusicManager.audioPlayer.stopTrack()
        PlaylistManager.deleteQuestionMessage()

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null

        ChannelManager.editStaticMessage("Derzeit wird nichts abgespielt", ConfigManager.data.defaultMessage, Color.RED, null)

        title?.let {
            ChannelManager.sendLog(title, text, member)
        }
    }

    fun join(channel: VoiceChannel?) {
        val audioManager = GuildManager.current?.audioManager ?: return

        if (audioManager.isConnected) {
            return
        }

        GuildManager.current.audioManager.openAudioConnection(channel)
        MusicManager.audioPlayer.volume = 10
    }
}
