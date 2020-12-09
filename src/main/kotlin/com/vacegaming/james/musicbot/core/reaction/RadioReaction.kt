package com.vacegaming.james.musicbot.core.reaction

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.vacegaming.james.musicbot.core.ChannelManager
import com.vacegaming.james.musicbot.core.GuildManager
import com.vacegaming.james.musicbot.core.MemberManager
import com.vacegaming.james.musicbot.core.VoiceChannelManager
import com.vacegaming.james.musicbot.core.music.MusicManager
import com.vacegaming.james.musicbot.core.music.MusicQueue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

object RadioReaction {
    var url = "http://weihnachten-high.rautemusik.fm/"
    var title = "Weihnachten FM"
    var desc = "Spritziger als Spritzgebäck"

    fun execute(member: Member) = GlobalScope.launch {
        val selfChannel = GuildManager.current?.selfMember?.voiceState?.channel
        val audioPlayer = MusicManager.audioPlayer
        val sendHandler = MusicManager.sendHandler

        selfChannel?.let {
            if (MemberManager.isInChannel(member).not()) return@launch
        }

        MusicManager.playerManager.loadItem(url, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                VoiceChannelManager
                    .join(member)
                    ?.apply {
                        this.sendingHandler = sendHandler
                    }?.run {
                        MusicQueue.queue.clear()
                        MusicManager.audioPlayer.startTrack(track, false)
                    }?.also {
                        ChannelManager.editStaticMessage(
                            title,
                            desc,
                            Color(209, 236, 241),
                            audioPlayer.volume
                        )
                    }
            }

            override fun playlistLoaded(playlist: AudioPlaylist?) {}
            override fun noMatches() {}
            override fun loadFailed(exception: FriendlyException?) {}
        })
    }
}
