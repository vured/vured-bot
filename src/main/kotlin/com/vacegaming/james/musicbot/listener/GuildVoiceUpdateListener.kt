package com.vacegaming.james.musicbot.listener

import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.GuildManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue
import com.vacegaming.james.musicbot.core.music.PlaylistManager
import com.vacegaming.james.musicbot.util.ConfigManager
import com.vacegaming.james.musicbot.util.DiscordClient
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildVoiceUpdateListener : ListenerAdapter() {

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        val memberSize = VoiceChannelManager.connectedChannel?.members?.size ?: 0
        val connectedVoice = GuildManager.current?.selfMember?.voiceState?.channel

        if (memberSize <= 1 || connectedVoice == null) {
            ChannelManager.sendLog("Stop", "Voicechannel war leer", null)
            MusicQueue.queue.clear()
            MusicManager.audioPlayer.stopTrack()
            VoiceChannelManager.leave()
            PlaylistManager.deleteQuestionMessage()
        }
    }
}
