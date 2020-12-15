package com.vacegaming.musicbot.core

import com.vacegaming.musicbot.core.music.MusicManager
import com.vacegaming.musicbot.core.music.MusicQueue
import com.vacegaming.musicbot.core.music.PlaylistManager
import com.vacegaming.musicbot.util.ConfigManager
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.AudioManager
import java.awt.Color

object VoiceChannelManager {
    fun leave(title: String? = null, text: String? = null, member: Member? = null) {
        val audioManager = GuildManager.current?.audioManager

        MusicQueue.queue.clear()
        MusicManager.audioPlayer.stopTrack()
        PlaylistManager.deleteQuestionMessage()

        audioManager?.closeAudioConnection()
        audioManager?.sendingHandler = null

        ChannelManager.editStaticMessage(
            "Derzeit wird nichts abgespielt",
            ConfigManager.data.defaultMessage,
            Color.RED,
            null
        )

        title?.let {
            ChannelManager.sendLog(title, text, member)
        }
    }

    fun join(member: Member?): AudioManager? {
        val memberVoiceState = member?.voiceState
        val selfVoiceState = GuildManager.current?.selfMember?.voiceState

        if (selfVoiceState?.inVoiceChannel() == true) {
            return GuildManager.current.audioManager
        }

        if (memberVoiceState?.inVoiceChannel() != true) {
            ChannelManager.sendMessage(Color.blue, "Bitte gehe zuerst in einen Voicechannel", 5000)
            return null
        }

        return memberVoiceState.channel?.run(::join)
    }

    private fun join(channel: VoiceChannel): AudioManager? {
        val audioManager = GuildManager.current?.audioManager
        val audioPlayer = MusicManager.audioPlayer

        audioManager?.openAudioConnection(channel)
        audioPlayer.volume = 10

        return audioManager
    }
}
