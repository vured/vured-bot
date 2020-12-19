package dev.jonaz.vacegaming.musicbot.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.jonaz.vacegaming.musicbot.service.MusicChannelService
import dev.jonaz.vacegaming.musicbot.service.MusicService
import dev.jonaz.vacegaming.musicbot.service.PlaylistService
import dev.jonaz.vacegaming.musicbot.service.VoiceChannelService
import dev.jonaz.vacegaming.musicbot.util.data.Config
import dev.jonaz.vacegaming.musicbot.util.data.Translation
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

class AudioLoadResultManager(
    private val member: Member
) : AudioLoadResultHandler {
    private val voiceChannelService by genericInject<VoiceChannelService>()
    private val musicChannelService by genericInject<MusicChannelService>()
    private val playlistService by genericInject<PlaylistService>()
    private val musicService by genericInject<MusicService>()

    override fun trackLoaded(track: AudioTrack) {
        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                musicService.queue(track)
            }
    }

    override fun playlistLoaded(playlist: AudioPlaylist) {
        val newTracks = playlist.tracks.take(Config.data.maxPlaylistTracks)

        voiceChannelService
            .join(member)
            ?.apply {
                this.sendingHandler = musicService.getSendHandler()
            }?.run {
                musicService.queue(playlist.selectedTrack ?: playlist.tracks[0])

                GlobalScope.launch {
                    playlistService.askToAdd(member, newTracks)
                }
            }
    }

    override fun noMatches() {
        musicChannelService.sendMessage(Color.RED, Translation.NO_MATCHES, 3000)
    }

    override fun loadFailed(exception: FriendlyException) {
        musicChannelService.sendMessage(Color.RED, Translation.LOAD_FAILED, 3000)
    }
}
